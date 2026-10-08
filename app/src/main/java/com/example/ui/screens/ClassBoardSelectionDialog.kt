package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EducationBoard
import com.example.data.model.SchoolClass
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy

@Composable
fun ClassBoardSelectionDialog(
    currentClass: String,
    currentBoard: String,
    studentName: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var selectedClass by remember { mutableStateOf(currentClass) }
    var selectedBoard by remember { mutableStateOf(currentBoard) }

    val classes = listOf(
        SchoolClass.CLASS_6,
        SchoolClass.CLASS_7,
        SchoolClass.CLASS_8,
        SchoolClass.CLASS_9,
        SchoolClass.CLASS_10,
        SchoolClass.CLASS_11,
        SchoolClass.CLASS_12
    )

    val boards = listOf(
        EducationBoard.BIHAR_BOARD,
        EducationBoard.CBSE,
        EducationBoard.UP_BOARD,
        EducationBoard.ICSE
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "कक्षा एवं Board का चयन",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Class Selection
                Text(
                    text = "आप किस कक्षा में हैं?",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    classes.forEach { cls ->
                        val isSelected = selectedClass == cls.displayName
                        val isAvailable = cls.isAvailable

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) AcademicBlueLight else CardBackground)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) AcademicBlue else BorderSubtle,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (isAvailable) {
                                        selectedClass = cls.displayName
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = cls.displayName,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isAvailable) PrimaryNavy else TextLightSlate
                            )
                            if (isAvailable) {
                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, null, tint = AcademicBlue, modifier = Modifier.size(18.dp))
                                } else {
                                    Text("सक्रिय", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Text("जल्द उपलब्ध", fontSize = 11.sp, color = TextLightSlate)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Board Selection
                Text(
                    text = "अपना Board चुनें",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    boards.forEach { brd ->
                        val isSelected = selectedBoard.contains(brd.displayName.substringBefore(" "))
                        val isAvailable = brd.isAvailable

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) AcademicBlueLight else CardBackground)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) AcademicBlue else BorderSubtle,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (isAvailable) {
                                        selectedBoard = brd.displayName
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = brd.displayName,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isAvailable) PrimaryNavy else TextLightSlate
                            )
                            if (isAvailable) {
                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, null, tint = AcademicBlue, modifier = Modifier.size(18.dp))
                                } else {
                                    Text("सक्रिय", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Text("जल्द उपलब्ध", fontSize = 11.sp, color = TextLightSlate)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(studentName, selectedClass, selectedBoard) },
                colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue),
                modifier = Modifier.testTag("save_class_board_btn")
            ) {
                Text("सुरक्षित करें")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("रद्द करें")
            }
        }
    )
}
