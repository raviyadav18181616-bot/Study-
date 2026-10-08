package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground

@Composable
fun DoubtSolverScreen(
    doubtQuery: String,
    doubtAnswer: String?,
    isLoading: Boolean,
    onQueryChange: (String) -> Unit,
    onAskDoubt: (String) -> Unit
) {
    val sampleDoubts = listOf(
        "यूक्लिड विभाजन प्रमेयिका क्या है?",
        "द्विघात बहुपद के शून्यक कैसे निकालें?",
        "उत्तल और अवतल लेंस में क्या अंतर है?",
        "विरंजक चूर्ण (Bleaching Powder) का सूत्र क्या है?",
        "नेफ्रॉन का क्या कार्य है?",
        "रक्त एवं लौह की नीति किसने अपनाई?"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("doubt_solver_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "सवाल पूछें (Doubt Solver)",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "कक्षा 10 के किसी भी विषय का सवाल लिखें, तुरंत सरल व्याख्या प्राप्त करें।",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                )
            }
        }

        // Input card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = doubtQuery,
                        onValueChange = onQueryChange,
                        placeholder = { Text("अपना सवाल यहाँ टाइप करें...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("doubt_query_input"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { onAskDoubt(doubtQuery) },
                        enabled = doubtQuery.isNotBlank() && !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("ask_doubt_button"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = CardBackground)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("समझाया जा रहा है...")
                        } else {
                            Icon(Icons.AutoMirrored.Filled.Send, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("समझाएँ / सवाल हल करें", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Quick Sample Doubt Pills
        item {
            Column {
                Text(
                    text = "अक्सर पूछे जाने वाले सवाल:",
                    style = MaterialTheme.typography.labelLarge.copy(color = TextLightSlate)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sampleDoubts) { doubt ->
                        Surface(
                            onClick = {
                                onQueryChange(doubt)
                                onAskDoubt(doubt)
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = CardBackground,
                            border = CardDefaults.outlinedCardBorder()
                        ) {
                            Text(
                                text = doubt,
                                fontSize = 12.sp,
                                color = PrimaryNavy,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Doubt Answer Card
        if (!doubtAnswer.isNullOrBlank()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("doubt_answer_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AcademicBlue))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = AcademicBlue,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "सरल व्याख्या एवं हल",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AcademicBlue
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = doubtAnswer,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = PrimaryNavy,
                                lineHeight = 26.sp
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
