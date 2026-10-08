package com.example.data.seed

import com.example.data.model.Question
import com.example.data.model.QuestionDifficulty
import com.example.data.model.QuestionType

object QuestionBankData {

    val questions: List<Question> = listOf(
        // === MATHS QUESTIONS ===
        Question(
            id = "q_m_01",
            subjectId = "maths",
            chapterId = "m_01",
            chapterName = "वास्तविक संख्याएँ",
            topicName = "यूक्लिड विभाजन प्रमेयिका",
            questionText = "यदि दो धनात्मक पूर्णांक a और b के लिए a = bq + r हो, तो r के लिए निम्नलिखित में कौन सत्य है?",
            options = listOf("0 ≤ r < b", "0 < r ≤ b", "0 < r < b", "r > b"),
            correctIndex = 0,
            explanation = "यूक्लिड विभाजन प्रमेयिका के अनुसार शेषफल r का मान 0 के बराबर या उससे बड़ा तथा भाजक b से छोटा होता है, अर्थात् 0 ≤ r < b.",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_02",
            subjectId = "maths",
            chapterId = "m_01",
            chapterName = "वास्तविक संख्याएँ",
            topicName = "अपरिमेय संख्याएँ",
            questionText = "निम्नलिखित में से कौन सी संख्या अपरिमेय है?",
            options = listOf("√16", "√25", "√20", "√49"),
            correctIndex = 2,
            explanation = "√20 = 2√5, जहाँ √5 अपरिमेय है, अतः √20 एक अपरिमेय संख्या है। अन्य सभी पूर्ण वर्ग हैं (√16=4, √25=5, √49=7).",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_03",
            subjectId = "maths",
            chapterId = "m_01",
            chapterName = "वास्तविक संख्याएँ",
            topicName = "दशमलव प्रसार",
            questionText = "परिमेय संख्या 35/50 का दशमलव प्रसार क्या होगा?",
            options = listOf("शांत", "अशांत आवर्ती", "अशांत अनावर्ती", "इनमें से कोई नहीं"),
            correctIndex = 0,
            explanation = "35/50 सरलतम रूप में 7/10 = 7/(2 × 5) है। चूँकि हर 2ⁿ × 5ᵐ के रूप का है, अतः इसका दशमलव प्रसार शांत होगा।",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_m_04",
            subjectId = "maths",
            chapterId = "m_01",
            chapterName = "वास्तविक संख्याएँ",
            topicName = "HCF और LCM संबंध",
            questionText = "यदि दो संख्याओं का HCF = 15 तथा LCM = 90 हो और एक संख्या 45 हो, तो दूसरी संख्या होगी:",
            options = listOf("30", "25", "35", "20"),
            correctIndex = 0,
            explanation = "सूत्र: पहली संख्या × दूसरी संख्या = HCF × LCM\n45 × दूसरी संख्या = 15 × 90\nदूसरी संख्या = (15 × 90) / 45 = 30.",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_m_05",
            subjectId = "maths",
            chapterId = "m_02",
            chapterName = "बहुपद",
            topicName = "शून्यकों का योग और गुणनफल",
            questionText = "द्विघात बहुपद x² - 3x + 2 के शून्यकों का योगफल क्या होगा?",
            options = listOf("3", "-3", "2", "-2"),
            correctIndex = 0,
            explanation = "द्विघात बहुपद ax² + bx + c के लिए शून्यकों का योग α + β = -b/a होता है। यहाँ a = 1, b = -3, अतः योग = -(-3)/1 = 3.",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_06",
            subjectId = "maths",
            chapterId = "m_02",
            chapterName = "बहुपद",
            topicName = "शून्यकों की संख्या",
            questionText = "एक रैखिक बहुपद ax + b का शून्यक क्या होता है?",
            options = listOf("-b/a", "b/a", "-a/b", "a/b"),
            correctIndex = 0,
            explanation = "ax + b = 0 ⇒ ax = -b ⇒ x = -b/a.",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_07",
            subjectId = "maths",
            chapterId = "m_03",
            chapterName = "दो चर वाले रैखिक समीकरण युग्म",
            topicName = "समीकरणों की संगतता",
            questionText = "यदि समीकरण युग्म a₁/a₂ ≠ b₁/b₂ हो, तो इस समीकरण युग्म का हल कैसा होगा?",
            options = listOf("अद्वितीय हल (केवल एक हल)", "अनंत हल", "कोई हल नहीं", "दो हल"),
            correctIndex = 0,
            explanation = "जब रेखाएँ एक-दूसरे को केवल एक बिंदु पर प्रतिच्छेद करती हैं, तो अद्वितीय हल प्राप्त होता है। शर्त: a₁/a₂ ≠ b₁/b₂.",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_m_08",
            subjectId = "maths",
            chapterId = "m_04",
            chapterName = "द्विघात समीकरण",
            topicName = "विविक्तकर और मूलों की प्रकृति",
            questionText = "द्विघात समीकरण 2x² - 4x + 3 = 0 का विविक्तकर (Discriminant) क्या होगा?",
            options = listOf("-8", "8", "-4", "16"),
            correctIndex = 0,
            explanation = "विविक्तकर D = b² - 4ac\nयहाँ a = 2, b = -4, c = 3\nD = (-4)² - 4(2)(3) = 16 - 24 = -8.",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_m_09",
            subjectId = "maths",
            chapterId = "m_05",
            chapterName = "समांतर श्रेढ़ियाँ",
            topicName = "nवाँ पद",
            questionText = "AP: 2, 7, 12, ... का 10वाँ पद क्या होगा?",
            options = listOf("47", "42", "50", "45"),
            correctIndex = 0,
            explanation = "प्रथम पद a = 2, सार्व अंतर d = 7 - 2 = 5.\na₁₀ = a + (10 - 1)d = 2 + 9(5) = 2 + 45 = 47.",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_10",
            subjectId = "maths",
            chapterId = "m_08",
            chapterName = "त्रिकोणमिति का परिचय",
            topicName = "त्रिकोणमितीय सर्वसमिकाएँ",
            questionText = "sin² 45° + cos² 45° का मान क्या होगा?",
            options = listOf("1", "0", "1/2", "2"),
            correctIndex = 0,
            explanation = "सर्वसमिका sin² θ + cos² θ = 1 सदैव सत्य होती है किसी भी कोण θ के लिए।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_11",
            subjectId = "maths",
            chapterId = "m_08",
            chapterName = "त्रिकोणमिति का परिचय",
            topicName = "पूरक कोण",
            questionText = "tan 65° / cot 25° का मान क्या होगा?",
            options = listOf("1", "0", "√3", "1/√3"),
            correctIndex = 0,
            explanation = "tan 65° = tan(90° - 25°) = cot 25°\nअतः cot 25° / cot 25° = 1.",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_m_12",
            subjectId = "maths",
            chapterId = "m_07",
            chapterName = "निर्देशांक ज्यामिति",
            topicName = "दूरी सूत्र",
            questionText = "मूल बिंदु (0, 0) से बिंदु P(3, 4) की दूरी क्या होगी?",
            options = listOf("5 मात्रक", "7 मात्रक", "25 मात्रक", "1 मात्रक"),
            correctIndex = 0,
            explanation = "मूल बिंदु से दूरी = √(x² + y²) = √(3² + 4²) = √(9 + 16) = √25 = 5 मात्रक।",
            difficulty = QuestionDifficulty.EASY
        ),

        // === SCIENCE QUESTIONS (Physics, Chemistry, Biology) ===
        // Physics
        Question(
            id = "q_s_p_01",
            subjectId = "science",
            subSubjectId = "physics",
            chapterId = "sci_phy_01",
            chapterName = "प्रकाश - परावर्तन तथा अपवर्तन",
            topicName = "दर्पण सूत्र व क्षमता",
            questionText = "उत्तल लेंस की क्षमता 2 डायोप्टर है, तो इसकी फोकस दूरी क्या होगी?",
            options = listOf("+50 सेमी", "-50 सेमी", "+20 सेमी", "-20 सेमी"),
            correctIndex = 0,
            explanation = "लेंस की क्षमता P = 1/f(m) ⇒ f = 1/P = 1/2 मीटर = 50 सेमी। उत्तल लेंस की फोकस दूरी धनात्मक होती है।",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_s_p_02",
            subjectId = "science",
            subSubjectId = "physics",
            chapterId = "sci_phy_01",
            chapterName = "प्रकाश - परावर्तन तथा अपवर्तन",
            topicName = "दर्पण के प्रकार",
            questionText = "वाहनों में पश्च-दृश्य (side mirror) के रूप में किस दर्पण का उपयोग किया जाता है?",
            options = listOf("उत्तल दर्पण", "अवतल दर्पण", "समतल दर्पण", "इनमें से कोई नहीं"),
            correctIndex = 0,
            explanation = "उत्तल दर्पण सदैव सीधा, छोटा आभासी प्रतिबिंब बनाता है और इसका दृष्टि क्षेत्र बहुत विस्तृत होता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_p_03",
            subjectId = "science",
            subSubjectId = "physics",
            chapterId = "sci_phy_02",
            chapterName = "मानव नेत्र तथा रंगबिरंगा संसार",
            topicName = "दृष्टि दोष",
            questionText = "सामान्य मानव नेत्र के लिए स्पष्ट दृष्टि की न्यूनतम दूरी क्या होती है?",
            options = listOf("25 सेमी", "25 मीटर", "अनंत", "2.5 सेमी"),
            correctIndex = 0,
            explanation = "स्वस्थ मानव नेत्र के लिए निकट बिंदु (Least distance of distinct vision) 25 सेमी होता है, जबकि दूर बिंदु अनंत होता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_p_04",
            subjectId = "science",
            subSubjectId = "physics",
            chapterId = "sci_phy_03",
            chapterName = "विद्युत (Electricity)",
            topicName = "ओम का नियम",
            questionText = "विद्युत प्रतिरोध का SI मात्रक क्या है?",
            options = listOf("ओम (Ω)", "एम्पियर (A)", "वोल्ट (V)", "कूलॉम (C)"),
            correctIndex = 0,
            explanation = "प्रतिरोध (Resistance) का SI मात्रक ओम (Ω) होता है। धारा का एम्पियर, विभवांतर का वोल्ट और आवेश का कूलॉम होता है।",
            difficulty = QuestionDifficulty.EASY
        ),

        // Chemistry
        Question(
            id = "q_s_c_01",
            subjectId = "science",
            subSubjectId = "chemistry",
            chapterId = "sci_chem_01",
            chapterName = "रासायनिक अभिक्रियाएँ एवं समीकरण",
            topicName = "अभिक्रिया के प्रकार",
            questionText = "Fe + CuSO₄ → FeSO₄ + Cu किस प्रकार की रासायनिक अभिक्रिया है?",
            options = listOf("विस्थापन अभिक्रिया", "संयोजन अभिक्रिया", "द्विविस्थापन अभिक्रिया", "वियोजन अभिक्रिया"),
            correctIndex = 0,
            explanation = "लोहा (Fe), कॉपर सल्फेट विलयन से कॉपर (Cu) को विस्थापित कर देता है क्योंकि Fe कॉपर से अधिक क्रियाशील है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_c_02",
            subjectId = "science",
            subSubjectId = "chemistry",
            chapterId = "sci_chem_02",
            chapterName = "अम्ल, क्षारक एवं लवण",
            topicName = "pH मान",
            questionText = "शुद्ध जल का pH मान कितना होता है?",
            options = listOf("7", "0", "14", "1"),
            correctIndex = 0,
            explanation = "शुद्ध जल उदासीन होता है, जिसका pH मान कमरे के तापमान पर ठीक 7 होता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_c_03",
            subjectId = "science",
            subSubjectId = "chemistry",
            chapterId = "sci_chem_02",
            chapterName = "अम्ल, क्षारक एवं लवण",
            topicName = "रासायनिक यौगिक सूत्र",
            questionText = "विरंजक चूर्ण (Bleaching Powder) का रासायनिक सूत्र क्या है?",
            options = listOf("CaOCl₂", "CaCO₃", "NaHCO₃", "Na₂CO₃·10H₂O"),
            correctIndex = 0,
            explanation = "शुष्क बुझे हुए चूने पर क्लोरीन गैस प्रवाहित करने पर विरंजक चूर्ण (CaOCl₂) बनता है।",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_s_c_04",
            subjectId = "science",
            subSubjectId = "chemistry",
            chapterId = "sci_chem_03",
            chapterName = "धातु एवं अधातु",
            topicName = "भौतिक गुण",
            questionText = "कमरे के ताप पर द्रव अवस्था में पाई जाने वाली धातु कौन सी है?",
            options = listOf("पारा (Mercury)", "ब्रोमीन", "लोहा", "सोडियम"),
            correctIndex = 0,
            explanation = "पारा (Hg) एकमात्र ऐसी धातु है जो कमरे के ताप पर द्रव अवस्था में पाई जाती है। ब्रोमीन द्रव अधातु है।",
            difficulty = QuestionDifficulty.EASY
        ),

        // Biology
        Question(
            id = "q_s_b_01",
            subjectId = "science",
            subSubjectId = "biology",
            chapterId = "sci_bio_01",
            chapterName = "जैव प्रक्रम",
            topicName = "उत्सर्जन",
            questionText = "वृक्क (Kidney) की रचनात्मक एवं क्रियात्मक इकाई क्या कहलाती है?",
            options = listOf("नेफ्रॉन (वृक्काणु)", "न्यूरॉन", "ग्लोमेरुलस", "बोमन संपुट"),
            correctIndex = 0,
            explanation = "वृक्क की सूक्ष्म नलिकाकार छानने वाली इकाई को नेफ्रॉन (Nephron/वृक्काणु) कहते हैं। न्यूरॉन तंत्रिका तंत्र की इकाई है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_b_02",
            subjectId = "science",
            subSubjectId = "biology",
            chapterId = "sci_bio_01",
            chapterName = "जैव प्रक्रम",
            topicName = "पादप परिवहन",
            questionText = "पौधों में जल एवं खनिज लवणों का संवहन किसके द्वारा होता है?",
            options = listOf("जाइलम (Xylem)", "फ्लोएम (Phloem)", "रंध्र (Stomata)", "क्लोरोप्लास्ट"),
            correctIndex = 0,
            explanation = "जाइलम ऊतक जड़ों से जल और खनिज लवणों को पत्तियों तक पहुँचाता है, जबकि फ्लोएम भोजन (सुक्रोज) का संवहन करता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_b_03",
            subjectId = "science",
            subSubjectId = "biology",
            chapterId = "sci_bio_02",
            chapterName = "नियंत्रण एवं समन्वय",
            topicName = "हार्मोन",
            questionText = "मानव में इंसुलिन हार्मोन की कमी से कौन सा रोग होता है?",
            options = listOf("मधुमेह (Diabetes)", "घेंघा (Goitre)", "रतौंधी", "एनीमिया"),
            correctIndex = 0,
            explanation = "अग्न्याशय के लैंगरहेंस द्वीपिकाओं से इंसुलिन स्रावित होता है जो रक्त में शर्करा को नियंत्रित करता है। इसकी कमी से मधुमेह होता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_s_b_04",
            subjectId = "science",
            subSubjectId = "biology",
            chapterId = "sci_bio_04",
            chapterName = "आनुवंशिकता एवं जैव विकास",
            topicName = "मेंडल के नियम",
            questionText = "आनुवंशिकी का जनक किसे कहा जाता है?",
            options = listOf("ग्रेगर जोहान मेंडल", "चार्ल्स डार्विन", "लामार्क", "रॉबर्ट हुक"),
            correctIndex = 0,
            explanation = "ग्रेगर जोहान मेंडल ने मटर के पौधों पर संकरण प्रयोग कर आनुवंशिकता के मूल नियमों की खोज की थी।",
            difficulty = QuestionDifficulty.EASY
        ),

        // === SOCIAL SCIENCE QUESTIONS (History, Geography, Civics, Economics, Disaster) ===
        // History
        Question(
            id = "q_sst_h_01",
            subjectId = "sst",
            subSubjectId = "history",
            chapterId = "sst_hist_01",
            chapterName = "यूरोप में राष्ट्रवाद",
            topicName = "इटली का एकीकरण",
            questionText = "रक्त एवं लौह (Blood and Iron) की नीति का अवलंबन किसने किया था?",
            options = listOf("बिस्मार्क", "मेजिनी", "हिटलर", "विलियम प्रथम"),
            correctIndex = 0,
            explanation = "प्रशा के चांसलर ऑटो वॉन बिस्मार्क ने जर्मनी के एकीकरण के लिए 'रक्त एवं लौह' की नीति अपनाई थी।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_sst_h_02",
            subjectId = "sst",
            subSubjectId = "history",
            chapterId = "sst_hist_04",
            chapterName = "भारत में राष्ट्रवाद",
            topicName = "जलियांवाला बाग",
            questionText = "जलियांवाला बाग हत्याकांड किस तिथि को हुआ था?",
            options = listOf("13 अप्रैल 1919", "14 अप्रैल 1919", "15 अप्रैल 1919", "13 मई 1919"),
            correctIndex = 0,
            explanation = "13 अप्रैल 1919 को बैसाखी के दिन अमृतसर के जलियांवाला बाग में शांतिपूर्ण सभा पर जनरल डायर ने गोलियां चलवाई थीं।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_sst_h_03",
            subjectId = "sst",
            subSubjectId = "history",
            chapterId = "sst_hist_04",
            chapterName = "भारत में राष्ट्रवाद",
            topicName = "असहयोग आंदोलन",
            questionText = "महात्मा गांधी ने साबरमती आश्रम से दांडी यात्रा किस आंदोलन के लिए शुरू की थी?",
            options = listOf("सविनय अवज्ञा आंदोलन", "असहयोग आंदोलन", "चंपारण सत्याग्रह", "भारत छोड़ो आंदोलन"),
            correctIndex = 0,
            explanation = "12 मार्च 1930 को गांधीजी ने दांडी मार्च शुरू किया और 6 अप्रैल 1930 को नमक कानून तोड़कर सविनय अवज्ञा आंदोलन का आरंभ किया।",
            difficulty = QuestionDifficulty.MEDIUM
        ),

        // Geography
        Question(
            id = "q_sst_g_01",
            subjectId = "sst",
            subSubjectId = "geography",
            chapterId = "sst_geo_01",
            chapterName = "भारत: संसाधन एवं उपयोग",
            topicName = "मृदा संसाधन",
            questionText = "काली मिट्टी का दूसरा नाम क्या है?",
            options = listOf("रेगुर मिट्टी (Regur)", "बलुई मिट्टी", "लाल मिट्टी", "लैटेराइट मिट्टी"),
            correctIndex = 0,
            explanation = "काली मिट्टी को रेगुर मिट्टी भी कहा जाता है, यह कपास की खेती के लिए सर्वाधिक उपयुक्त मानी जाती है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_sst_g_02",
            subjectId = "sst",
            subSubjectId = "geography",
            chapterId = "sst_geo_05",
            chapterName = "बिहार: कृषि एवं वन संसाधन",
            topicName = "बिहार की कृषि",
            questionText = "बिहार के कितने प्रतिशत भौगोलिक क्षेत्र में वन का विस्तार है?",
            options = listOf("लगभग 7%", "15%", "20%", "33%"),
            correctIndex = 0,
            explanation = "बिहार राज्य में वन आच्छादन कुल भौगोलिक क्षेत्र का लगभग 7% (7.84%) ही है।",
            difficulty = QuestionDifficulty.MEDIUM
        ),

        // Civics
        Question(
            id = "q_sst_c_01",
            subjectId = "sst",
            subSubjectId = "civics",
            chapterId = "sst_civ_01",
            chapterName = "लोकतंत्र में सत्ता की साझेदारी",
            topicName = "सामाजिक विभाजन",
            questionText = "भारत में महिलाओं के लिए 50% आरक्षण की व्यवस्था किस संस्था में है?",
            options = listOf("पंचायती राज व्यवस्था में", "लोकसभा में", "विधानसभा में", "राज्यसभा में"),
            correctIndex = 0,
            explanation = "बिहार देश का पहला राज्य है जिसने पंचायती राज संस्थाओं में महिलाओं के लिए 50% आरक्षण का प्रावधान किया।",
            difficulty = QuestionDifficulty.EASY
        ),

        // Economics
        Question(
            id = "q_sst_e_01",
            subjectId = "sst",
            subSubjectId = "economics",
            chapterId = "sst_eco_01",
            chapterName = "अर्थव्यवस्था एवं इतिहास",
            topicName = "अर्थव्यवस्था के क्षेत्र",
            questionText = "प्राथमिक क्षेत्र (Primary Sector) के अंतर्गत कौन सी गतिविधि आती है?",
            options = listOf("कृषि, पशुपालन एवं मत्स्य पालन", "विनिर्माण उद्योग", "बैंकिंग एवं बीमा", "यातायात सेवाएं"),
            correctIndex = 0,
            explanation = "कृषि, वानिकी और पशुपालन प्राथमिक क्षेत्र हैं। उद्योग द्वितीयक क्षेत्र में तथा सेवाएं तृतीयक क्षेत्र में आती हैं।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_sst_e_02",
            subjectId = "sst",
            subSubjectId = "economics",
            chapterId = "sst_eco_03",
            chapterName = "मुद्रा, बचत एवं साख",
            topicName = "मुद्रा के कार्य",
            questionText = "भारत का केंद्रीय बैंक कौन सा है?",
            options = listOf("भारतीय रिजर्व बैंक (RBI)", "भारतीय स्टेट बैंक (SBI)", "पंजाब नेशनल बैंक", "इलाहाबाद बैंक"),
            correctIndex = 0,
            explanation = "भारतीय रिजर्व बैंक (Reserve Bank of India) भारत का केंद्रीय बैंक है, जिसकी स्थापना 1 अप्रैल 1935 को हुई थी।",
            difficulty = QuestionDifficulty.EASY
        ),

        // Disaster Management
        Question(
            id = "q_sst_d_01",
            subjectId = "sst",
            subSubjectId = "disaster",
            chapterId = "sst_dis_02",
            chapterName = "बाढ़ और सुखाड़",
            topicName = "बाढ़ प्रबंधन",
            questionText = "बिहार का कौन सा क्षेत्र बाढ़ग्रस्त क्षेत्र माना जाता है?",
            options = listOf("उत्तरी बिहार", "दक्षिणी बिहार", "पूर्वी बिहार", "पश्चिमी बिहार"),
            correctIndex = 0,
            explanation = "कोसी, गंडक, बागमती जैसी नदियों के कारण उत्तरी बिहार प्रतिवर्ष भीषण बाढ़ से प्रभावित होता है।",
            difficulty = QuestionDifficulty.EASY
        ),

        // === HINDI QUESTIONS (Literature & Grammar) ===
        Question(
            id = "q_h_01",
            subjectId = "hindi",
            chapterId = "hin_lit_01",
            chapterName = "श्रम विभाजन और जाति प्रथा",
            topicName = "लेखक परिचय एवं मूल भाव",
            questionText = "'श्रम विभाजन और जाति प्रथा' के लेखक कौन हैं?",
            options = listOf("बाबा साहेब डॉ. भीमराव आंबेडकर", "रामविलास शर्मा", "गुणाकर मुले", "महात्मा गांधी"),
            correctIndex = 0,
            explanation = "यह पाठ बाबा साहेब डॉ. भीमराव आंबेडकर के विख्यात भाषण 'Annihilation of Caste' का ललई सिंह यादव द्वारा किया गया हिंदी रूपांतरण है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_h_02",
            subjectId = "hindi",
            chapterId = "hin_lit_02",
            chapterName = "विष के दांत",
            topicName = "कहानी एवं पात्र",
            questionText = "'विष के दांत' कहानी के मुख्य पात्र काशू (खोखा) के दो दांत किसने तोड़े थे?",
            options = listOf("मदन ने", "सेन साहब ने", "गिरधर ने", "आलो ने"),
            correctIndex = 0,
            explanation = "लट्टू नचाने के विवाद में मदन ने खोखा (काशू) पर मुक्कों से प्रहार कर उसके दो दांत तोड़ दिए थे।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_h_03",
            subjectId = "hindi",
            chapterId = "hin_var_01",
            chapterName = "दही वाली मंगम्मा",
            topicName = "पात्र एवं परिवेश",
            questionText = "दही वाली मंगम्मा किस भाषा की अनूदित कहानी है?",
            options = listOf("कन्नड़", "तमिल", "तेलुगु", "उड़िया"),
            correctIndex = 0,
            explanation = "'दही वाली मंगम्मा' कन्नड़ भाषा के प्रसिद्ध कथाकार श्रीनिवास की लोकप्रिय कहानी है जिसका अनुवाद बी. आर. नारायण ने किया है।",
            difficulty = QuestionDifficulty.MEDIUM
        ),
        Question(
            id = "q_h_04",
            subjectId = "hindi",
            chapterId = "hin_gr_03",
            chapterName = "संधि",
            topicName = "स्वर संधि",
            questionText = "'विद्यालय' शब्द का सही संधि विच्छेद क्या होगा?",
            options = listOf("विद्या + आलय", "विद्य + आलय", "विद्या + लय", "विद् + आलय"),
            correctIndex = 0,
            explanation = "विद्या (आ) + आलय (आ) = विद्यालय (दीर्घ स्वर संधि: आ + आ = आ).",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_h_05",
            subjectId = "hindi",
            chapterId = "hin_gr_04",
            chapterName = "समास",
            topicName = "समास भेद",
            questionText = "'लंबोदर' किस समास का उदाहरण है?",
            options = listOf("बहुव्रीहि समास", "द्वंद्व समास", "तत्पुरुष समास", "अव्ययीभाव समास"),
            correctIndex = 0,
            explanation = "लंबा है उदर जिनका अर्थात् भगवान गणेश। जब दोनों पद मिलकर किसी तीसरे पद की ओर संकेत करें, तो बहुव्रीहि समास होता है।",
            difficulty = QuestionDifficulty.EASY
        ),
        Question(
            id = "q_h_06",
            subjectId = "hindi",
            chapterId = "hin_gr_07",
            chapterName = "मुहावरे",
            topicName = "मुहावरे का अर्थ",
            questionText = "'अंगूठा दिखाना' मुहावरे का सही अर्थ क्या है?",
            options = listOf("साफ मना कर देना या इनकार करना", "मजाक उड़ाना", "धोखा देना", "प्रशंसा करना"),
            correctIndex = 0,
            explanation = "'अंगूठा दिखाना' का अर्थ समय पर काम न आना या साफ इनकार करना होता है।",
            difficulty = QuestionDifficulty.EASY
        )
    )

    fun getQuestionsBySubject(subjectId: String): List<Question> {
        return questions.filter { it.subjectId == subjectId }
    }

    fun getQuestionsByChapter(chapterId: String): List<Question> {
        val chapterQuestions = questions.filter { it.chapterId == chapterId }
        if (chapterQuestions.isNotEmpty()) return chapterQuestions
        // Fallback to subject questions if a specific chapter has fewer seed questions
        val chapter = BiharBoardCurriculumData.getChapterById(chapterId)
        val subjectId = chapter?.subjectId ?: "maths"
        return questions.filter { it.subjectId == subjectId }
    }
}
