package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.ui.screen.component.MemorialBanner
import com.nohyam.memorial.ui.theme.Background
import com.nohyam.memorial.ui.theme.DarkText
import com.nohyam.memorial.ui.theme.GreenAccent
import com.nohyam.memorial.ui.theme.NavyButton
import com.nohyam.memorial.ui.theme.PurpleAccent
import com.nohyam.memorial.ui.theme.RedAccent
import com.nohyam.memorial.ui.theme.SoftGreen
import com.nohyam.memorial.ui.theme.SoftPurple
import com.nohyam.memorial.ui.theme.SoftRed
import com.nohyam.memorial.ui.theme.SubText


@Composable
fun QuizResultScreen(
    total: Int,
    bingo: Int,
    records: List<RecordEntity>,
    onHomeClick: () -> Unit
) {
    Scaffold(
        containerColor = Background
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                QuizResultBanner()
            }

            item {
                QuizResultSummary(
                    total = total,
                    bingo = bingo,
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    )
                )
            }

            item {
                Text(
                    text = "질문별 결과",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    )
                )
            }

            items(
                items = records,
                key = { it.id }
            ) { record ->
                QuizResultCard(
                    record = record,
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    )
                )
            }

            item {
                Button(
                    onClick = onHomeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyButton
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        text = "홈으로 돌아가기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
private fun QuizResultBanner() {
    MemorialBanner(
        height = 245.dp,
        topContent = {
            Text(
                text = "Memorial",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        bottomContent = {
            Text(
                text = "오늘의 기억 퀴즈 완료!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "어제의 순간을 얼마나 기억했는지\n함께 확인해볼까요?",
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = Color(0xFFE0E5F4)
            )
        }
    )
}


@Composable
private fun QuizResultSummary(
    total: Int,
    bingo: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (total > 0) {
        bingo.toFloat() / total
    } else {
        0f
    }

    val percentage = if (total > 0) {
        (progress * 100).toInt()
    } else {
        0
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(SoftPurple)
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(112.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                color = PurpleAccent,
                trackColor = Color(0xFFD6DCF0),
                strokeWidth = 9.dp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "기억한 질문",
                    fontSize = 11.sp,
                    color = SubText
                )

                Text(
                    text = "$bingo / $total",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "기억한 비율",
                fontSize = 14.sp,
                color = SubText
            )

            Text(
                text = "$percentage%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleAccent
            )

            Text(
                text = "오늘도 기억을\n돌아보았어요.",
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = DarkText
            )
        }
    }
}

@Composable
private fun QuizResultCard(
    record: RecordEntity,
    modifier: Modifier = Modifier
) {
    val isRemembered = record.isRemembered == true

    val accentColor = if (isRemembered) {
        GreenAccent
    } else {
        RedAccent
    }

    val badgeBackground = if (isRemembered) {
        SoftGreen
    } else {
        SoftRed
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = if (isRemembered) {
                Icons.Default.CheckCircle
            } else {
                Icons.Default.Close
            },
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = record.question,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 21.sp,
            color = DarkText,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(badgeBackground)
                .padding(
                    horizontal = 10.dp,
                    vertical = 7.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isRemembered) {
                    "기억함"
                } else {
                    "기억 못함"
                },
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizResultScreenPreview() {
    QuizResultScreen(
        total = 3,
        bingo = 2,
        records = listOf(
            RecordEntity(
                id = 1L,
                question = "어제 저녁에 뭐 먹었지?",
                answer = "치킨",
                createdAt = System.currentTimeMillis(),
                isRemembered = true
            ),
            RecordEntity(
                id = 2L,
                question = "어제 가장 즐거웠던 순간은?",
                answer = "친구와 산책한 시간",
                createdAt = System.currentTimeMillis(),
                isRemembered = false
            ),
            RecordEntity(
                id = 3L,
                question = "어제 누구를 만났지?",
                answer = "오랜만에 만난 친구",
                createdAt = System.currentTimeMillis(),
                isRemembered = true
            )
        ),
        onHomeClick = {}
    )
}
