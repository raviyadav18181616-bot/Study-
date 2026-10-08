package com.example.data.ai

import com.example.BuildConfig
import com.example.data.model.PracticeMiniQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {

    private const val MODEL_NAME = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val isKeyAvailable: Boolean
        get() = try {
            val key = BuildConfig.GEMINI_API_KEY
            key.isNotBlank() && key != "MY_GEMINI_API_KEY"
        } catch (_: Exception) {
            false
        }

    suspend fun solveDoubt(userQuery: String, subjectHint: String = "सामान्य"): String = withContext(Dispatchers.IO) {
        if (!isKeyAvailable) {
            return@withContext getOfflineDoubtSolution(userQuery, subjectHint)
        }

        try {
            val prompt = """
                आप बिहार बोर्ड कक्षा 10 के एक अत्यंत सरल और अनुभवी शिक्षक हैं।
                विद्यार्थी का प्रश्न: "$userQuery"
                विषय संदर्भ: $subjectHint

                कृपया उत्तर को निम्नलिखित स्पष्ट प्रारूप में देवनागरी हिंदी में दें:
                1. मुख्य उत्तर / सूत्र (यदि लागू हो)
                2. सरल व्याख्या (स्टेप-बाय-स्टेप)
                3. एक उदाहरण या याद रखने योग्य मुख्य बात
                बहुत बड़ा निबंध न लिखें, बिल्कुल स्पष्ट और छात्र-सुलभ रखें।
            """.trimIndent()

            val responseText = callGeminiApi(prompt)
            if (responseText.isNotBlank()) responseText else getOfflineDoubtSolution(userQuery, subjectHint)
        } catch (e: Exception) {
            getOfflineDoubtSolution(userQuery, subjectHint)
        }
    }

    suspend fun generateSimilarPracticeQuestion(topicName: String, subjectName: String): PracticeMiniQuestion = withContext(Dispatchers.IO) {
        if (!isKeyAvailable) {
            return@withContext getOfflineSimilarQuestion(topicName)
        }

        try {
            val prompt = """
                बिहार बोर्ड कक्षा 10 $subjectName के टॉपिक "$topicName" पर आधारित एक नया बहुविकल्पीय प्रश्न (MCQ) बनाएँ।
                केवल JSON प्रारूप में उत्तर दें बिना किसी मार्कडाउन या बैकर्टिक्स के:
                {
                  "question": "प्रश्न हिंदी में",
                  "options": ["विकल्प A", "विकल्प B", "विकल्प C", "विकल्प D"],
                  "correctIndex": 0,
                  "explanation": "सरल व्याख्या हिंदी में"
                }
            """.trimIndent()

            val rawJson = callGeminiApi(prompt)
            val cleanJson = rawJson.replace("```json", "").replace("```", "").trim()
            val obj = JSONObject(cleanJson)
            val question = obj.getString("question")
            val optArray = obj.getJSONArray("options")
            val options = mutableListOf<String>()
            for (i in 0 until optArray.length()) {
                options.add(optArray.getString(i))
            }
            val correctIndex = obj.getInt("correctIndex")
            val explanation = obj.getString("explanation")

            PracticeMiniQuestion(question, options, correctIndex, explanation)
        } catch (e: Exception) {
            getOfflineSimilarQuestion(topicName)
        }
    }

    private fun callGeminiApi(promptText: String): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val url = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"

        val rootJson = JSONObject().apply {
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()
            val partObj = JSONObject().apply {
                put("text", promptText)
            }
            partsArray.put(partObj)
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            put("contents", contentsArray)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = rootJson.toString().toRequestBody(mediaType)
        val request = Request.Builder().url(url).post(body).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return ""
            val bodyString = response.body?.string() ?: return ""
            val json = JSONObject(bodyString)
            val candidates = json.optJSONArray("candidates") ?: return ""
            if (candidates.length() == 0) return ""
            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.optJSONObject("content") ?: return ""
            val parts = content.optJSONArray("parts") ?: return ""
            if (parts.length() == 0) return ""
            return parts.getJSONObject(0).optString("text", "")
        }
    }

    private fun getOfflineDoubtSolution(query: String, subject: String): String {
        val lower = query.lowercase()
        return when {
            lower.contains("यूक्लिड") || lower.contains("hcf") -> {
                "📌 यूक्लिड विभाजन प्रमेयिका:\n" +
                        "दो धनात्मक पूर्णांक a और b के लिए: a = bq + r (जहाँ 0 ≤ r < b)\n\n" +
                        "चरण-दर-चरण विधि:\n" +
                        "1. बड़ी संख्या को भाज्य (a) और छोटी को भाजक (b) मानें।\n" +
                        "2. विभाजन करें और शेषफल r ज्ञात करें।\n" +
                        "3. जब r = 0 हो जाए, तब अंतिम भाजक ही अभीष्ट HCF होगा।\n\n" +
                        "💡 मुख्य बिंदु: यह विधि केवल धनात्मक पूर्णांकों के लिए लागू होती है।"
            }
            lower.contains("शून्यक") || lower.contains("बहुपद") -> {
                "📌 द्विघात बहुपद के शून्यक:\n" +
                        "यदि बहुपद ax² + bx + c = 0 हो, तो:\n" +
                        "• शून्यकों का योग (α + β) = -b/a\n" +
                        "• शून्यकों का गुणनफल (αβ) = c/a\n\n" +
                        "💡 उदाहरण: x² - 5x + 6 में शून्यकों का योग = -(-5)/1 = 5 तथा गुणनफल = 6/1 = 6."
            }
            lower.contains("प्रकाश") || lower.contains("लेंस") || lower.contains("दर्पण") -> {
                "📌 प्रकाश एवं लेंस/दर्पण सूत्र:\n" +
                        "• दर्पण सूत्र: 1/v + 1/u = 1/f\n" +
                        "• लेंस सूत्र: 1/v - 1/u = 1/f\n" +
                        "• लेंस क्षमता P = 1/f(मीटर में) [मात्रक: डायोप्टर (D)]\n\n" +
                        "💡 ध्यान दें: अवतल दर्पण/लेंस की फोकस दूरी ऋणात्मक (-) तथा उत्तल की धनात्मक (+) होती है।"
            }
            lower.contains("अम्ल") || lower.contains("क्षार") || lower.contains("ph") -> {
                "📌 अम्ल, क्षारक एवं pH स्केल:\n" +
                        "• pH < 7 : अम्लीय (उदा. HCl, सिरका)\n" +
                        "• pH = 7 : उदासीन (उदा. शुद्ध जल)\n" +
                        "• pH > 7 : क्षारीय (उदा. NaOH)\n\n" +
                        "💡 लिटमस पत्र नियम: अम्ल नीले लिटमस को लाल करता है, जबकि क्षारक लाल लिटमस को नीला करता है।"
            }
            else -> {
                "📌 संक्षेप में उत्तर:\n" +
                        "आपके प्रश्न \"$query\" के लिए मुख्य संकल्पना:\n" +
                        "बिहार बोर्ड परीक्षा में इस विषय से प्रायः 1 अंक के वस्तुनिष्ठ प्रश्न और 2 अंक के लघु उत्तरीय प्रश्न पूछे जाते हैं।\n\n" +
                        "1. मूलभूत परिभाषा और सूत्र को अच्छी तरह याद रखें।\n" +
                        "2. संबंधित अध्याय के मुख्य उदाहरण हल करें।\n" +
                        "3. बोर्ड पैटर्न टेस्ट देकर अपनी समझ जाँचें।"
            }
        }
    }

    private fun getOfflineSimilarQuestion(topicName: String): PracticeMiniQuestion {
        return PracticeMiniQuestion(
            question = "$topicName पर आधारित अभ्यास प्रश्न: निम्न में से कौन सा कथन सही है?",
            options = listOf(
                "यह बोर्ड परीक्षा की मुख्य संकल्पना है",
                "इसका मान सदैव अपरिवर्तित रहता है",
                "यह केवल काल्पनिक परिस्थितियों में सत्य है",
                "इनमें से कोई नहीं"
            ),
            correctIndex = 0,
            explanation = "यह संकल्पना बिहार बोर्ड के पाठ्यक्रम के अनुसार अत्यंत महत्वपूर्ण है और बार-बार पूछी जाती है।"
        )
    }
}
