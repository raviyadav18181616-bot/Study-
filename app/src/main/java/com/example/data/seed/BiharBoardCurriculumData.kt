package com.example.data.seed

import com.example.data.model.Chapter
import com.example.data.model.PracticeMiniQuestion
import com.example.data.model.SubSubject
import com.example.data.model.Topic

object BiharBoardCurriculumData {

    val scienceSubSubjects = listOf(
        SubSubject("physics", "भौतिक विज्ञान", "Physics", 5),
        SubSubject("chemistry", "रसायन विज्ञान", "Chemistry", 5),
        SubSubject("biology", "जीव विज्ञान", "Biology", 6)
    )

    val sstSubSubjects = listOf(
        SubSubject("history", "इतिहास (भारत व समकालीन विश्व)", "History", 8),
        SubSubject("geography", "भूगोल (भारत: संसाधन एवं उपयोग)", "Geography", 6),
        SubSubject("civics", "राजनीति विज्ञान (लोकतांत्रिक राजनीति)", "Civics", 5),
        SubSubject("economics", "अर्थशास्त्र (हमारी अर्थव्यवस्था)", "Economics", 7),
        SubSubject("disaster", "आपदा प्रबंधन", "Disaster Management", 5)
    )

    val hindiSubSubjects = listOf(
        SubSubject("literature_godhuli", "गोधूलि भाग 2 (गद्य एवं पद्य)", "Literature", 12),
        SubSubject("literature_varnika", "वर्णिका भाग 2 (पूरक पाठ्यपुस्तक)", "Supplementary", 5),
        SubSubject("grammar", "व्याकरण (वर्ण, संधि, समास, कारक)", "Grammar", 8),
        SubSubject("composition", "रचना एवं पत्र लेखन", "Writing", 4)
    )

    // Mathematics Chapters (Bihar Board Class 10 - 15 Chapters)
    val mathsChapters = listOf(
        Chapter(
            id = "m_01",
            number = 1,
            subjectId = "maths",
            titleHindi = "वास्तविक संख्याएँ",
            titleEnglish = "Real Numbers",
            topicsCount = 4,
            topics = listOf(
                Topic(
                    id = "m_01_t1",
                    title = "यूक्लिड विभाजन प्रमेयिका (Euclid's Division Lemma)",
                    shortSummary = "दो धनात्मक पूर्णांक a और b के लिए a = bq + r जहाँ 0 ≤ r < b",
                    explanation = "यूक्लिड विभाजन प्रमेयिका के अनुसार, किन्हीं दो धनात्मक पूर्णांकों a और b के लिए, ऐसी अद्वितीय पूर्ण संख्याएँ q और r विद्यमान हैं कि a = bq + r, जहाँ 0 ≤ r < b होता है। इसका मुख्य उपयोग दो संख्याओं का महत्तम समापवर्तक (HCF) ज्ञात करने में किया जाता है।",
                    formulaOrKeyPoint = "a = bq + r (जहाँ 0 ≤ r < b)",
                    example = "उदाहरण: 135 और 225 का HCF निकालें:\n225 = 135 × 1 + 90\n135 = 90 × 1 + 45\n90 = 45 × 2 + 0\nअतः HCF = 45",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "यदि a = bq + r, तो r के लिए कौन सा संबंध सत्य है?",
                        options = listOf("0 < r ≤ b", "0 ≤ r < b", "0 < r < b", "r > b"),
                        correctIndex = 1,
                        explanation = "यूक्लिड प्रमेयिका के अनुसार शेषफल r हमेशा 0 या 0 से बड़ा और भाजक b से छोटा होता है (0 ≤ r < b)।"
                    )
                ),
                Topic(
                    id = "m_01_t2",
                    title = "अंकगणित की आधारभूत प्रमेय (Fundamental Theorem of Arithmetic)",
                    shortSummary = "प्रत्येक भाज्य संख्या को अभाज्य संख्याओं के गुणनफल के रूप में व्यक्त किया जा सकता है।",
                    explanation = "प्रत्येक भाज्य संख्या को अभाज्य गुणनखंडों के एक गुणनफल के रूप में अद्वितीय रूप से गुणनखंडित किया जा सकता है, बिना गुणनखंडों के क्रम पर ध्यान दिए। HCF(a, b) × LCM(a, b) = a × b होता है।",
                    formulaOrKeyPoint = "HCF(a, b) × LCM(a, b) = a × b",
                    example = "संख्या 12 = 2² × 3 और 18 = 2 × 3²।\nHCF = 2 × 3 = 6\nLCM = 2² × 3² = 36\nजाँच: 6 × 36 = 216 और 12 × 18 = 216",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "दो संख्याओं का गुणनफल 8670 है और उनका HCF 17 है, तो LCM क्या होगा?",
                        options = listOf("510", "420", "610", "340"),
                        correctIndex = 0,
                        explanation = "LCM = गुणनफल / HCF = 8670 / 17 = 510."
                    )
                ),
                Topic(
                    id = "m_01_t3",
                    title = "अपरिमेय संख्याओं का पुनर्भ्रमण (Irrational Numbers)",
                    shortSummary = "√2, √3, √5 आदि अपरिमेय संख्याएँ हैं।",
                    explanation = "यदि p एक अभाज्य संख्या है और p, a² को विभाजित करती है, तो p, a को भी विभाजित करेगी जहाँ a एक धनात्मक पूर्णांक है। विरोधाभास विधि (Contradiction Method) से सिद्ध किया जाता है कि √2 या √5 अपरिमेय है।",
                    formulaOrKeyPoint = "परिमेय + अपरिमेय = सदा अपरिमेय (जैसे 3 + √5)",
                    example = "प्रश्न: क्या (2 + √3) एक परिमेय संख्या है?\nउत्तर: नहीं, यह अपरिमेय संख्या है क्योंकि एक परिमेय और एक अपरिमेय संख्या का योग सदैव अपरिमेय होता है।",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "निम्नलिखित में से कौन सी संख्या अपरिमेय है?",
                        options = listOf("√4", "√9", "√7", "√16"),
                        correctIndex = 2,
                        explanation = "√7 का पूर्ण वर्गमूल नहीं निकलता, अतः यह एक अपरिमेय संख्या है।"
                    )
                ),
                Topic(
                    id = "m_01_t4",
                    title = "परिमेय संख्याओं के दशमलव प्रसार (Decimal Expansions)",
                    shortSummary = "शांत (Terminating) vs अशांत आवर्ती (Non-terminating Repeating)",
                    explanation = "यदि किसी परिमेय संख्या p/q में q के अभाज्य गुणनखंड 2ⁿ 5ᵐ के रूप के हों, जहाँ n और m ऋणेतर पूर्णांक हैं, तो उसका दशमलव प्रसार शांत होता है। अन्यथा अशांत आवर्ती होता है।",
                    formulaOrKeyPoint = "शांत दशमलव के लिए हर q का रूप: 2ⁿ × 5ᵐ",
                    example = "13/3125: हर 3125 = 5⁵ = 2⁰ × 5⁵ (शांत दशमलव)\n17/8 = 17/(2³) (शांत दशमलव)",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "17/8 का दशमलव प्रसार कैसा होगा?",
                        options = listOf("शांत", "अशांत आवर्ती", "अशांत अनावर्ती", "इनमें से कोई नहीं"),
                        correctIndex = 0,
                        explanation = "हर 8 = 2³, जो 2ⁿ 5ᵐ के रूप में है, अतः यह शांत है।"
                    )
                )
            ),
            revisionSummary = "1. a = bq + r (0 ≤ r < b)\n2. HCF × LCM = a × b\n3. p/q शांत होगा यदि q = 2ⁿ 5ᵐ\n4. √2, √3, √5 अपरिमेय संख्याएँ हैं।",
            importantFormulas = listOf("a = bq + r", "HCF(a,b) × LCM(a,b) = a × b", "q = 2ⁿ × 5ᵐ (शांत)")
        ),
        Chapter(
            id = "m_02",
            number = 2,
            subjectId = "maths",
            titleHindi = "बहुपद",
            titleEnglish = "Polynomials",
            topicsCount = 3,
            topics = listOf(
                Topic(
                    id = "m_02_t1",
                    title = "बहुपद के शून्यकों का ज्यामितीय अर्थ",
                    shortSummary = "किसी बहुपद y = p(x) के शून्यक x-अक्ष पर प्रतिच्छेद बिंदुओं की संख्या के बराबर होते हैं।",
                    explanation = "एक रैखिक बहुपद ax + b का शून्यक -b/a होता है। द्विघात बहुपद ax² + bx + c के अधिक से अधिक 2 शून्यक हो सकते हैं। परवलय x-अक्ष को जितने बिंदुओं पर काटता है, उतने ही शून्यक होते हैं।",
                    formulaOrKeyPoint = "रैखिक का शून्यक = -b/a",
                    example = "द्विघात बहुपद के आलेख को परवलय (Parabola) कहते हैं।",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "यदि कोई आलेख x-अक्ष को 3 बिंदुओं पर काटता है, तो शून्यकों की संख्या होगी:",
                        options = listOf("1", "2", "3", "0"),
                        correctIndex = 2,
                        explanation = "x-अक्ष पर प्रतिच्छेद बिंदुओं की संख्या ही शून्यकों की संख्या होती है।"
                    )
                ),
                Topic(
                    id = "m_02_t2",
                    title = "शून्यकों और गुणांकों में संबंध",
                    shortSummary = "शून्यकों का योग (α + β) = -b/a तथा गुणनफल (αβ) = c/a",
                    explanation = "यदि α और β द्विघात बहुपद ax² + bx + c के शून्यक हों, तो शून्यकों का योग α + β = -b/a और शून्यकों का गुणनफल αβ = c/a होता है। त्रिघात बहुपद के लिए α+β+γ = -b/a, αβ+βγ+γα = c/a, αβγ = -d/a होता है।",
                    formulaOrKeyPoint = "α + β = -b/a,  αβ = c/a",
                    example = "बहुपद x² - 2x - 8 के लिए:\na=1, b=-2, c=-8\nα+β = -(-2)/1 = 2, αβ = -8/1 = -8",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "बहुपद 2x² - 8x + 6 के शून्यकों का योगफल क्या होगा?",
                        options = listOf("4", "-4", "3", "-3"),
                        correctIndex = 0,
                        explanation = "शून्यकों का योग = -b/a = -(-8)/2 = 4."
                    )
                ),
                Topic(
                    id = "m_02_t3",
                    title = "बहुपदों के लिए विभाजन एल्गोरिद्म",
                    shortSummary = "p(x) = g(x) × q(x) + r(x), जहाँ r(x) = 0 या घात r(x) < घात g(x)",
                    explanation = "यदि p(x) और g(x) दो बहुपद हैं जहाँ g(x) ≠ 0, तो बहुपद q(x) और r(x) प्राप्त होते हैं।",
                    formulaOrKeyPoint = "भाज्य = भाजक × भागफल + शेषफल",
                    example = "बहुपद निर्माण सूत्र: k[x² - (α + β)x + αβ]",
                    practiceQuestion = PracticeMiniQuestion(
                        question = "एक द्विघात बहुपद जिसके शून्यकों का योग 3 और गुणनफल 2 है, वह होगा:",
                        options = listOf("x² - 3x + 2", "x² + 3x + 2", "x² - 2x + 3", "x² + 3x - 2"),
                        correctIndex = 0,
                        explanation = "सूत्र: x² - (योग)x + (गुणनफल) = x² - 3x + 2."
                    )
                )
            ),
            revisionSummary = "1. द्विघात बहुपद के शून्यक α, β\n2. α + β = -b/a\n3. αβ = c/a\n4. बहुपद = x² - (α+β)x + αβ",
            importantFormulas = listOf("α + β = -b/a", "αβ = c/a", "x² - (α+β)x + αβ")
        ),
        Chapter(
            id = "m_03",
            number = 3,
            subjectId = "maths",
            titleHindi = "दो चर वाले रैखिक समीकरण युग्म",
            titleEnglish = "Pair of Linear Equations in Two Variables",
            topicsCount = 3,
            revisionSummary = "1. प्रतिच्छेदी रेखाएं (अद्वितीय हल): a₁/a₂ ≠ b₁/b₂\n2. संपाती रेखाएं (अनेक हल): a₁/a₂ = b₁/b₂ = c₁/c₂\n3. समांतर रेखाएं (कोई हल नहीं): a₁/a₂ = b₁/b₂ ≠ c₁/c₂",
            importantFormulas = listOf("a₁/a₂ ≠ b₁/b₂ (अद्वितीय हल)", "a₁/a₂ = b₁/b₂ ≠ c₁/c₂ (असंगत, कोई हल नहीं)")
        ),
        Chapter(
            id = "m_04",
            number = 4,
            subjectId = "maths",
            titleHindi = "द्विघात समीकरण",
            titleEnglish = "Quadratic Equations",
            topicsCount = 3,
            revisionSummary = "1. मानक रूप: ax² + bx + c = 0 (a ≠ 0)\n2. विविक्तकर (Discriminant) D = b² - 4ac\n3. D > 0: दो भिन्न वास्तविक मूल\n4. D = 0: दो बराबर वास्तविक मूल (-b/2a)\n5. D < 0: कोई वास्तविक मूल नहीं",
            importantFormulas = listOf("x = (-b ± √D) / (2a)", "D = b² - 4ac")
        ),
        Chapter(
            id = "m_05",
            number = 5,
            subjectId = "maths",
            titleHindi = "समांतर श्रेढ़ियाँ (AP)",
            titleEnglish = "Arithmetic Progressions",
            topicsCount = 2,
            revisionSummary = "1. nवाँ पद: aₙ = a + (n - 1)d\n2. प्रथम n पदों का योग: Sₙ = n/2 [2a + (n - 1)d] या Sₙ = n/2 [a + l]",
            importantFormulas = listOf("aₙ = a + (n - 1)d", "Sₙ = n/2 [2a + (n - 1)d]")
        ),
        Chapter(
            id = "m_06",
            number = 6,
            subjectId = "maths",
            titleHindi = "त्रिभुज",
            titleEnglish = "Triangles",
            topicsCount = 3,
            revisionSummary = "1. थेल्स प्रमेय (BPT): यदि रेखा भुजा के समांतर हो तो अन्य दो भुजाएँ एक ही अनुपात में विभाजित होती हैं।\n2. समरूपता कसौटियाँ: AAA, SSS, SAS\n3. पाइथागोरस प्रमेय: कर्ण² = लम्ब² + आधार²",
            importantFormulas = listOf("AD/DB = AE/EC (थेल्स प्रमेय)", "h² = p² + b²")
        ),
        Chapter(
            id = "m_07",
            number = 7,
            subjectId = "maths",
            titleHindi = "निर्देशांक ज्यामिति",
            titleEnglish = "Coordinate Geometry",
            topicsCount = 3,
            revisionSummary = "1. दूरी सूत्र: d = √[(x₂ - x₁)² + (y₂ - y₁)²]\n2. विभाजन सूत्र: x = (m₁x₂ + m₂x₁)/(m₁+m₂), y = (m₁y₂ + m₂y₁)/(m₁+m₂)\n3. मध्य बिंदु: ((x₁+x₂)/2, (y₁+y₂)/2)",
            importantFormulas = listOf("d = √[(x₂ - x₁)² + (y₂ - y₁)²]", "मध्य बिंदु = ((x₁+x₂)/2, (y₁+y₂)/2)")
        ),
        Chapter(
            id = "m_08",
            number = 8,
            subjectId = "maths",
            titleHindi = "त्रिकोणमिति का परिचय",
            titleEnglish = "Introduction to Trigonometry",
            topicsCount = 4,
            revisionSummary = "1. sin θ = P/H, cos θ = B/H, tan θ = P/B\n2. sin²θ + cos²θ = 1\n3. 1 + tan²θ = sec²θ\n4. 1 + cot²θ = cosec²θ\n5. sin(90° - θ) = cos θ, tan(90° - θ) = cot θ",
            importantFormulas = listOf("sin²θ + cos²θ = 1", "sec²θ - tan²θ = 1", "cosec²θ - cot²θ = 1")
        ),
        Chapter(
            id = "m_09",
            number = 9,
            subjectId = "maths",
            titleHindi = "त्रिकोणमिति के कुछ अनुप्रयोग (ऊँचाई और दूरी)",
            titleEnglish = "Some Applications of Trigonometry",
            topicsCount = 2,
            revisionSummary = "1. उन्नयन कोण (Angle of Elevation)\n2. अवनमन कोण (Angle of Depression)\n3. दृष्टि रेखा (Line of sight)",
            importantFormulas = listOf("tan θ = ऊँचाई / आधार")
        ),
        Chapter(
            id = "m_10",
            number = 10,
            subjectId = "maths",
            titleHindi = "वृत्त",
            titleEnglish = "Circles",
            topicsCount = 2,
            revisionSummary = "1. स्पर्श रेखा स्पर्श बिंदु से जाने वाली त्रिज्या पर लंब होती है।\n2. बाह्य बिंदु से खींची गई दोनों स्पर्श रेखाओं की लंबाइयाँ बराबर होती हैं।",
            importantFormulas = listOf("स्पर्श रेखा ⊥ त्रिज्या", "PA = PB")
        ),
        Chapter(
            id = "m_11",
            number = 11,
            subjectId = "maths",
            titleHindi = "रचनाएँ",
            titleEnglish = "Constructions",
            topicsCount = 2,
            revisionSummary = "1. रेखाखंड का दिए गए अनुपात में विभाजन\n2. वृत्त पर बाह्य बिंदु से स्पर्श रेखा युग्म की रचना",
            importantFormulas = emptyList()
        ),
        Chapter(
            id = "m_12",
            number = 12,
            subjectId = "maths",
            titleHindi = "वृत्तों से संबंधित क्षेत्रफल",
            titleEnglish = "Areas Related to Circles",
            topicsCount = 3,
            revisionSummary = "1. वृत्त का परिमाप = 2πr, क्षेत्रफल = πr²\n2. त्रिज्यखंड का क्षेत्रफल = (θ/360°) × πr²\n3. चाप की लंबाई = (θ/360°) × 2πr",
            importantFormulas = listOf("क्षेत्रफल = πr²", "त्रिज्यखंड = (θ/360°) × πr²", "चाप = (θ/360°) × 2πr")
        ),
        Chapter(
            id = "m_13",
            number = 13,
            subjectId = "maths",
            titleHindi = "पृष्ठीय क्षेत्रफल और आयतन",
            titleEnglish = "Surface Areas and Volumes",
            topicsCount = 4,
            revisionSummary = "1. बेलन का आयतन = πr²h, वक्र पृष्ठ = 2πrh\n2. शंकु का आयतन = (1/3)πr²h, l = √(r² + h²)\n3. गोला का आयतन = (4/3)πr³, पृष्ठ = 4πr²",
            importantFormulas = listOf("बेलन आयतन = πr²h", "शंकु आयतन = (1/3)πr²h", "गोला आयतन = (4/3)πr³")
        ),
        Chapter(
            id = "m_14",
            number = 14,
            subjectId = "maths",
            titleHindi = "सांख्यिकी",
            titleEnglish = "Statistics",
            topicsCount = 3,
            revisionSummary = "1. माध्य (Mean) = Σfᵢxᵢ / Σfᵢ\n2. बहुलक (Mode) = l + [(f₁ - f₀) / (2f₁ - f₀ - f₂)] × h\n3. माध्यक (Median) = l + [((n/2) - cf) / f] × h\n4. संबंध: 3 माध्यक = बहुलक + 2 माध्य",
            importantFormulas = listOf("3 माध्यक = बहुलक + 2 माध्य", "माध्य = Σfᵢxᵢ / Σfᵢ")
        ),
        Chapter(
            id = "m_15",
            number = 15,
            subjectId = "maths",
            titleHindi = "प्रायिकता",
            titleEnglish = "Probability",
            topicsCount = 2,
            revisionSummary = "1. P(E) = अनुकूल परिणामों की संख्या / कुल परिणामों की संख्या\n2. 0 ≤ P(E) ≤ 1\n3. निश्चित घटना की प्रायिकता = 1, असंभव घटना की प्रायिकता = 0\n4. P(E) + P(not E) = 1",
            importantFormulas = listOf("P(E) + P(E̅) = 1", "0 ≤ P(E) ≤ 1")
        )
    )

    // Science Chapters (Bihar Board Class 10: Physics, Chemistry, Biology)
    val scienceChapters = listOf(
        // Physics
        Chapter(
            id = "sci_phy_01",
            number = 1,
            subjectId = "science",
            subSubjectId = "physics",
            titleHindi = "प्रकाश - परावर्तन तथा अपवर्तन",
            titleEnglish = "Light - Reflection and Refraction",
            topicsCount = 3,
            revisionSummary = "1. दर्पण सूत्र: 1/v + 1/u = 1/f\n2. आवर्धन m = -v/u = h'/h\n3. लेंस सूत्र: 1/v - 1/u = 1/f\n4. लेंस की क्षमता P = 1/f(m) (मात्रक: डायोप्टर D)\n5. स्नेल का नियम: sin i / sin r = स्थिरांक (μ)",
            importantFormulas = listOf("1/v + 1/u = 1/f (दर्पण)", "1/v - 1/u = 1/f (लेंस)", "P = 1/f (डायोप्टर)")
        ),
        Chapter(
            id = "sci_phy_02",
            number = 2,
            subjectId = "science",
            subSubjectId = "physics",
            titleHindi = "मानव नेत्र तथा रंगबिरंगा संसार",
            titleEnglish = "Human Eye and Colourful World",
            topicsCount = 3,
            revisionSummary = "1. स्पष्ट दृष्टि की न्यूनतम दूरी = 25 सेमी\n2. निकट दृष्टि दोष (Myopia) - अवतल लेंस द्वारा निवारण\n3. दूर दृष्टि दोष (Hypermetropia) - उत्तल लेंस द्वारा निवारण\n4. वर्ण विक्षेपण (Dispersion): लाल रंग का विचलन सबसे कम, बैंगनी का सबसे अधिक।",
            importantFormulas = listOf("न्यूनतम दूरी = 25 cm", "विबग्योर (VIBGYOR)")
        ),
        Chapter(
            id = "sci_phy_03",
            number = 3,
            subjectId = "science",
            subSubjectId = "physics",
            titleHindi = "विद्युत (Electricity)",
            titleEnglish = "Electricity",
            topicsCount = 4,
            revisionSummary = "1. विद्युत धारा I = Q/t (एम्पियर)\n2. विभवांतर V = W/Q (वोल्ट)\n3. ओम का नियम: V = IR\n4. प्रतिरोधकता R = ρ(l/A)\n5. श्रेणीक्रम: R = R₁ + R₂ + R₃\n6. समांतर क्रम: 1/R = 1/R₁ + 1/R₂ + 1/R₃\n7. जूल का तापीय नियम: H = I²Rt",
            importantFormulas = listOf("V = IR (ओम का नियम)", "H = I²Rt", "P = VI = I²R = V²/R")
        ),
        Chapter(
            id = "sci_phy_04",
            number = 4,
            subjectId = "science",
            subSubjectId = "physics",
            titleHindi = "विद्युत धारा के चुंबकीय प्रभाव",
            titleEnglish = "Magnetic Effects of Electric Current",
            topicsCount = 3,
            revisionSummary = "1. फ्लेमिंग का वामहस्त नियम (विद्युत मोटर)\n2. फ्लेमिंग का दक्षिण-हस्त नियम (विद्युत जनित्र)\n3. घरेलू परिपथ में भूसंपर्क तार, फ्यूज सुरक्षा उपाय हैं।",
            importantFormulas = listOf("फ्लेमिंग का वामहस्त नियम (FBI)")
        ),
        Chapter(
            id = "sci_phy_05",
            number = 5,
            subjectId = "science",
            subSubjectId = "physics",
            titleHindi = "ऊर्जा के स्रोत",
            titleEnglish = "Sources of Energy",
            topicsCount = 2,
            revisionSummary = "1. नवीकरणीय स्रोत: सौर ऊर्जा, पवन ऊर्जा, जल ऊर्जा\n2. अनवीकरणीय स्रोत: कोयला, पेट्रोलियम, प्राकृतिक गैस\n3. बायोगैस का मुख्य घटक: मीथेन (CH₄, 75%)",
            importantFormulas = emptyList()
        ),

        // Chemistry
        Chapter(
            id = "sci_chem_01",
            number = 1,
            subjectId = "science",
            subSubjectId = "chemistry",
            titleHindi = "रासायनिक अभिक्रियाएँ एवं समीकरण",
            titleEnglish = "Chemical Reactions and Equations",
            topicsCount = 3,
            revisionSummary = "1. संयोजन अभिक्रिया: A + B → AB\n2. वियोजन अभिक्रिया: AB → A + B\n3. विस्थापन: Fe + CuSO₄ → FeSO₄ + Cu\n4. उपचयन (Oxidation): O₂ का योग या H₂ का ह्रास\n5. अपचयन (Reduction): H₂ का योग या O₂ का ह्रास",
            importantFormulas = listOf("Fe + CuSO₄ → FeSO₄ + Cu", "CaO + H₂O → Ca(OH)₂ + ऊष्मा")
        ),
        Chapter(
            id = "sci_chem_02",
            number = 2,
            subjectId = "science",
            subSubjectId = "chemistry",
            titleHindi = "अम्ल, क्षारक एवं लवण",
            titleEnglish = "Acids, Bases and Salts",
            topicsCount = 4,
            revisionSummary = "1. pH स्केल: 0 से 14 (अम्ल < 7, उदासीन = 7, क्षारक > 7)\n2. लिटमस: अम्ल नीला को लाल करता है, क्षार लाल को नीला\n3. विरंजक चूर्ण: CaOCl₂\n4. प्लास्टर ऑफ पेरिस: CaSO₄ · ½H₂O\n5. बेकिंग सोडा: NaHCO₃\n6. धोने का सोडा: Na₂CO₃ · 10H₂O",
            importantFormulas = listOf("pH = -log[H⁺]", "CaOCl₂ (विरंजक चूर्ण)", "CaSO₄·½H₂O (POP)")
        ),
        Chapter(
            id = "sci_chem_03",
            number = 3,
            subjectId = "science",
            subSubjectId = "chemistry",
            titleHindi = "धातु एवं अधातु",
            titleEnglish = "Metals and Non-metals",
            topicsCount = 3,
            revisionSummary = "1. पारा (Hg) कमरे के ताप पर द्रव धातु है। ब्रोमीन (Br) द्रव अधातु है।\n2. सक्रियता श्रेणी में सबसे अधिक क्रियाशील: K > Na > Ca > Mg\n3. ऐक्वारेजिया: 3 HCl : 1 HNO₃\n4. निस्तापन और भर्जन द्वारा अयस्कों का सांद्रण।",
            importantFormulas = listOf("3 HCl + 1 HNO₃ (ऐक्वारेजिया)")
        ),
        Chapter(
            id = "sci_chem_04",
            number = 4,
            subjectId = "science",
            subSubjectId = "chemistry",
            titleHindi = "कार्बन एवं उसके यौगिक",
            titleEnglish = "Carbon and its Compounds",
            topicsCount = 4,
            revisionSummary = "1. कार्बन की संयोजकता = 4 (चतुःसंयोजकता)\n2. ऐल्केन: CₙH₂ₙ₊₂, ऐल्कीन: CₙH₂ₙ, ऐल्काइन: CₙH₂ₙ₋₂\n3. साबुन और अपमार्जक: मिसेल (Micelle) निर्माण।",
            importantFormulas = listOf("CₙH₂ₙ₊₂ (ऐल्केन)", "CₙH₂ₙ (ऐल्कीन)", "CₙH₂ₙ₋₂ (ऐल्काइन)")
        ),
        Chapter(
            id = "sci_chem_05",
            number = 5,
            subjectId = "science",
            subSubjectId = "chemistry",
            titleHindi = "तत्वों का आवर्त वर्गीकरण",
            titleEnglish = "Periodic Classification of Elements",
            topicsCount = 3,
            revisionSummary = "1. मेंडेलीव का आवर्त नियम: परमाणु द्रव्यमान पर आधारित\n2. आधुनिक आवर्त नियम (मोजले): परमाणु संख्या पर आधारित\n3. आधुनिक आवर्त सारणी में 18 वर्ग (Groups) और 7 आवर्त (Periods) हैं।",
            importantFormulas = listOf("18 वर्ग, 7 आवर्त")
        ),

        // Biology
        Chapter(
            id = "sci_bio_01",
            number = 1,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "जैव प्रक्रम (Life Processes)",
            titleEnglish = "Life Processes",
            topicsCount = 4,
            revisionSummary = "1. पोषण: स्वपोषी (प्रकाश संश्लेषण: 6CO₂ + 12H₂O → C₆H₁₂O₆ + 6O₂ + 6H₂O) एवं विषमपोषी।\n2. श्वसन: वायवीय (38 ATP) एवं अवायवीय (2 ATP)\n3. परिवहन: जाइलम (जल), फ्लोएम (भोजन); मानव हृदय में 4 कोष्ठ।\n4. उत्सर्जन: वृक्क (Kidney) की रचनात्मक एवं क्रियात्मक इकाई नेफ्रॉन (Nephron) है।",
            importantFormulas = listOf("6CO₂ + 12H₂O → C₆H₁₂O₆ + 6O₂ + 6H₂O", "नेफ्रॉन (उत्सर्जन इकाई)")
        ),
        Chapter(
            id = "sci_bio_02",
            number = 2,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "नियंत्रण एवं समन्वय",
            titleEnglish = "Control and Coordination",
            topicsCount = 3,
            revisionSummary = "1. तंत्रिका कोशिका (न्यूरॉन) शरीर की सबसे बड़ी कोशिका है।\n2. पादप हार्मोन: ऑक्सिन, जिबरेलिन, साइटोकाइनिन (वृद्धि प्रेरक), एब्सिसिक अम्ल (वृद्धि रोधक)।\n3. मानव अंतःस्रावी ग्रंथियाँ: थाइरॉइड (थाइरोक्सिन), अग्न्याशय (इंसुलिन)।",
            importantFormulas = listOf("न्यूरॉन → सिनैप्स", "इंसुलिन की कमी से मधुमेह")
        ),
        Chapter(
            id = "sci_bio_03",
            number = 3,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "जीव जनन कैसे करते हैं",
            titleEnglish = "How do Organisms Reproduce",
            topicsCount = 3,
            revisionSummary = "1. अलैंगिक जनन: विखंडन (अमीबा), मुकुलन (यीस्ट, हाइड्रा), पुनरुद्भवन (प्लेनेरिया)।\n2. लैंगिक जनन: परागण (स्व-परागण, पर-परागण), निषेचन।\n3. मानव प्रजनन तंत्र एवं परिवार नियोजन के उपाय।",
            importantFormulas = emptyList()
        ),
        Chapter(
            id = "sci_bio_04",
            number = 4,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "आनुवंशिकता एवं जैव विकास",
            titleEnglish = "Heredity and Evolution",
            topicsCount = 3,
            revisionSummary = "1. मेंडल को आनुवंशिकी का जनक कहा जाता है।\n2. मटर के पौधे (Pisum sativum) पर प्रयोग।\n3. एकसंकर संकरण अनुपात (Monohybrid): 3:1 (लक्षणप्ररूपी) व 1:2:1 (जीनप्ररूपी)।\n4. मानव में लिंग निर्धारण: पुरुष XY, महिला XX.",
            importantFormulas = listOf("Monohybrid = 3:1", "पुरुष = XY, महिला = XX")
        ),
        Chapter(
            id = "sci_bio_05",
            number = 5,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "हमारा पर्यावरण",
            titleEnglish = "Our Environment",
            topicsCount = 2,
            revisionSummary = "1. आहार श्रृंखला (Food Chain) एवं 10% ऊर्जा का नियम (लिंडेमान)।\n2. ओजोन परत (O₃) पराबैंगनी किरणों से रक्षा करती है; CFC द्वारा क्षरण।\n3. जैव निम्नीकरणीय एवं अजैव निम्नीकरणीय अपशिष्ट।",
            importantFormulas = listOf("10% ऊर्जा नियम", "O₃ (ओजोन)")
        ),
        Chapter(
            id = "sci_bio_06",
            number = 6,
            subjectId = "science",
            subSubjectId = "biology",
            titleHindi = "प्राकृतिक संसाधनों का संपोषित प्रबंधन",
            titleEnglish = "Sustainable Management of Natural Resources",
            topicsCount = 2,
            revisionSummary = "1. 5 R सिद्धांत: Refuse, Reduce, Reuse, Repurpose, Recycle.\n2. चिपको आंदोलन (सुंदरलाल बहुगुणा)।\n3. गंगा कार्य योजना (GAP) 1985.",
            importantFormulas = listOf("5R सिद्धांत", "चिपको आंदोलन")
        )
    )

    // Social Science Chapters (Bihar Board Class 10: History, Geography, Civics, Economics, Disaster Management)
    val sstChapters = listOf(
        // History
        Chapter("sst_hist_01", 1, "sst", "history", "यूरोप में राष्ट्रवाद", "Nationalism in Europe", 3),
        Chapter("sst_hist_02", 2, "sst", "history", "समाजवाद एवं साम्यवाद", "Socialism and Communism", 3),
        Chapter("sst_hist_03", 3, "sst", "history", "हिंद-चीन में राष्ट्रवादी आंदोलन", "Nationalist Movement in Indo-China", 3),
        Chapter("sst_hist_04", 4, "sst", "history", "भारत में राष्ट्रवाद", "Nationalism in India", 4),
        Chapter("sst_hist_05", 5, "sst", "history", "अर्थव्यवस्था और आजीविका", "Economy and Livelihoods", 3),
        Chapter("sst_hist_06", 6, "sst", "history", "शहरीकरण एवं शहरी जीवन", "Urbanisation and Urban Life", 2),
        Chapter("sst_hist_07", 7, "sst", "history", "व्यापार और भूमंडलीकरण", "Trade and Globalisation", 3),
        Chapter("sst_hist_08", 8, "sst", "history", "प्रेस-संस्कृति एवं राष्ट्रवाद", "Print Culture and Nationalism", 2),

        // Geography
        Chapter("sst_geo_01", 1, "sst", "geography", "भारत: संसाधन एवं उपयोग", "Resources and Utilisation", 4),
        Chapter("sst_geo_02", 2, "sst", "geography", "कृषि (Agriculture)", "Agriculture", 3),
        Chapter("sst_geo_03", 3, "sst", "geography", "निर्माण उद्योग", "Manufacturing Industries", 3),
        Chapter("sst_geo_04", 4, "sst", "geography", "परिवहन, संचार एवं व्यापार", "Transport, Communication and Trade", 3),
        Chapter("sst_geo_05", 5, "sst", "geography", "बिहार: कृषि एवं वन संसाधन", "Bihar: Agriculture and Forest", 3),
        Chapter("sst_geo_06", 6, "sst", "geography", "मानचित्र अध्ययन", "Map Study", 2),

        // Civics
        Chapter("sst_civ_01", 1, "sst", "civics", "लोकतंत्र में सत्ता की साझेदारी", "Power Sharing in Democracy", 3),
        Chapter("sst_civ_02", 2, "sst", "civics", "सत्ता में साझेदारी की कार्यप्रणाली", "Working of Power Sharing", 3),
        Chapter("sst_civ_03", 3, "sst", "civics", "लोकतंत्र में प्रतिस्पर्धा एवं संघर्ष", "Competition and Contests", 3),
        Chapter("sst_civ_04", 4, "sst", "civics", "लोकतंत्र की उपलब्धियाँ", "Outcomes of Democracy", 2),
        Chapter("sst_civ_05", 5, "sst", "civics", "लोकतंत्र की चुनौतियाँ", "Challenges to Democracy", 2),

        // Economics
        Chapter("sst_eco_01", 1, "sst", "economics", "अर्थव्यवस्था एवं इसके विकास का इतिहास", "Economy and Its Development History", 3),
        Chapter("sst_eco_02", 2, "sst", "economics", "राज्य एवं राष्ट्र की आय", "State and National Income", 3),
        Chapter("sst_eco_03", 3, "sst", "economics", "मुद्रा, बचत एवं साख", "Money, Savings and Credit", 3),
        Chapter("sst_eco_04", 4, "sst", "economics", "हमारी वित्तीय संस्थाएँ", "Our Financial Institutions", 3),
        Chapter("sst_eco_05", 5, "sst", "economics", "रोजगार एवं सेवाएँ", "Employment and Services", 2),
        Chapter("sst_eco_06", 6, "sst", "economics", "वैश्वीकरण (Globalisation)", "Globalisation", 2),
        Chapter("sst_eco_07", 7, "sst", "economics", "उपभोक्ता जागरण एवं संरक्षण", "Consumer Rights and Awareness", 2),

        // Disaster Management
        Chapter("sst_dis_01", 1, "sst", "disaster", "प्राकृतिक आपदा: एक परिचय", "Natural Disaster Introduction", 2),
        Chapter("sst_dis_02", 2, "sst", "disaster", "बाढ़ और सुखाड़", "Floods and Drought", 2),
        Chapter("sst_dis_03", 3, "sst", "disaster", "भूकंप एवं सुनामी", "Earthquake and Tsunami", 2),
        Chapter("sst_dis_04", 4, "sst", "disaster", "जीवन रक्षक आकस्मिक प्रबंधन", "Emergency Life Saving Management", 2),
        Chapter("sst_dis_05", 5, "sst", "disaster", "आपदा काल में वैकल्पिक संचार", "Alternative Communication in Disasters", 2)
    )

    // Hindi Chapters (Bihar Board Class 10: Godhuli & Varnika & Vyakaran)
    val hindiChapters = listOf(
        Chapter("hin_lit_01", 1, "hindi", "literature_godhuli", "श्रम विभाजन और जाति प्रथा", "Dr. B.R. Ambedkar", 3),
        Chapter("hin_lit_02", 2, "hindi", "literature_godhuli", "विष के दांत", "Nalin Vilochan Sharma", 3),
        Chapter("hin_lit_03", 3, "hindi", "literature_godhuli", "भारत से हम क्या सीखें", "Max Muller", 3),
        Chapter("hin_lit_04", 4, "hindi", "literature_godhuli", "नाखून क्यों बढ़ते हैं", "Hazari Prasad Dwivedi", 2),
        Chapter("hin_lit_05", 5, "hindi", "literature_godhuli", "नागरी लिपि", "Gunakar Mule", 2),
        Chapter("hin_lit_06", 6, "hindi", "literature_godhuli", "बहादुर", "Amarkant", 3),
        Chapter("hin_lit_07", 7, "hindi", "literature_godhuli", "परंपरा का मूल्यांकन", "Ram Vilas Sharma", 2),
        Chapter("hin_lit_08", 8, "hindi", "literature_godhuli", "जित-जित मैं निरखत हूँ", "Pt. Birju Maharaj", 2),
        Chapter("hin_lit_09", 9, "hindi", "literature_godhuli", "आविन्यों", "Ashok Vajpeyi", 2),
        Chapter("hin_lit_10", 10, "hindi", "literature_godhuli", "मछली", "Vinod Kumar Shukla", 2),
        Chapter("hin_lit_11", 11, "hindi", "literature_godhuli", "नौबतखाने में इबादत", "Yatindra Mishra", 2),
        Chapter("hin_lit_12", 12, "hindi", "literature_godhuli", "शिक्षा और संस्कृति", "Mahatma Gandhi", 2),
        Chapter("hin_var_01", 1, "hindi", "literature_varnika", "दही वाली मंगम्मा", "Srinivas", 2),
        Chapter("hin_var_02", 2, "hindi", "literature_varnika", "ढहते विश्वास", "Satkori Hota", 2),
        Chapter("hin_var_03", 3, "hindi", "literature_varnika", "माँ (ईश्वर पेटलीकर)", "Ishwar Petlikar", 2),
        Chapter("hin_var_04", 4, "hindi", "literature_varnika", "नगर (सुजाता)", "Sujatha", 2),
        Chapter("hin_var_05", 5, "hindi", "literature_varnika", "धरती कब तक घूमेगी", "Sanwar Daiya", 2),
        Chapter("hin_gr_01", 1, "hindi", "grammar", "वर्ण, उच्चारण स्थान एवं वर्तनी", "Phonetics", 2),
        Chapter("hin_gr_02", 2, "hindi", "grammar", "संज्ञा, सर्वनाम एवं विशेषण", "Parts of Speech", 3),
        Chapter("hin_gr_03", 3, "hindi", "grammar", "संधि (स्वर, व्यंजन, विसर्ग)", "Sandhi", 3),
        Chapter("hin_gr_04", 4, "hindi", "grammar", "समास (अव्ययीभाव, तत्पुरुष, द्वंद्व, द्विगु, कर्मधारय, बहुव्रीहि)", "Samas", 3),
        Chapter("hin_gr_05", 5, "hindi", "grammar", "कारक एवं विभक्ति", "Karak", 2),
        Chapter("hin_gr_06", 6, "hindi", "grammar", "उपसर्ग एवं प्रत्यय", "Prefix and Suffix", 2),
        Chapter("hin_gr_07", 7, "hindi", "grammar", "मुहावरे एवं लोकोक्तियाँ", "Idioms and Proverbs", 2),
        Chapter("hin_gr_08", 8, "hindi", "grammar", "पर्यायवाची एवं विलोम शब्द", "Synonyms & Antonyms", 2)
    )

    fun getAllChaptersForSubject(subjectId: String): List<Chapter> {
        return when (subjectId) {
            "maths" -> mathsChapters
            "science" -> scienceChapters
            "sst" -> sstChapters
            "hindi" -> hindiChapters
            else -> emptyList()
        }
    }

    fun getChapterById(chapterId: String): Chapter? {
        return (mathsChapters + scienceChapters + sstChapters + hindiChapters).find { it.id == chapterId }
    }
}
