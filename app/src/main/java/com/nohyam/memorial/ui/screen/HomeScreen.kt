package com.nohyam.memorial.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.ui.screen.component.MemorialBanner
import com.nohyam.memorial.ui.state.HomeUiState
import com.nohyam.memorial.ui.state.QuizState
import com.nohyam.memorial.ui.theme.Background
import com.nohyam.memorial.ui.theme.DarkText
import com.nohyam.memorial.ui.theme.GreenAccent
import com.nohyam.memorial.ui.theme.PurpleAccent
import com.nohyam.memorial.ui.theme.SoftGreen
import com.nohyam.memorial.ui.theme.SoftPurple

@Composable
fun HomeScreen(
    homeUiState: HomeUiState, onQuizClick: () -> Unit, onRecordClick: () -> Unit
) {
    Scaffold(
        containerColor = Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())

        ) {
            HomeBanner()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "나의 기억 공간",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                val quizTitle = when (homeUiState.quizState) {
                    QuizState.NONE -> "오늘은 퀴즈가 없어요."

                    QuizState.AVAILABLE -> "어제의 기록이 기다리고 있어요."

                    QuizState.COMPLETED -> "오늘의 퀴즈를 완료했어요."
                }

                val quizDescription = when (homeUiState.quizState) {
                    QuizState.NONE -> "오늘 기록하면 내일 다시 만날 수 있어요."

                    QuizState.AVAILABLE -> "지금 바로 기억을 떠올려보세요."

                    QuizState.COMPLETED -> "내일 새로운 기억을 확인해보세요."
                }

                MemorialActionCard(
                    title = "다음으로 기억할 질문",
                    mainText = quizTitle,
                    description = quizDescription,
                    buttonText = when(homeUiState.quizState){
                        QuizState.NONE -> "퀴즈 없음"
                        QuizState.AVAILABLE -> "퀴즈 풀기"
                        QuizState.COMPLETED -> "퀴즈 다시 풀기"
                    },
                    backgroundColor = SoftPurple,
                    accentColor = PurpleAccent,
                    icon = Icons.AutoMirrored.Outlined.MenuBook,
                    onClick = onQuizClick,
                    enabled = homeUiState.quizState != QuizState.NONE,

                )

                MemorialActionCard(
                    title = "오늘의 기록",
                    mainText = "오늘 ${homeUiState.recordCount}개의 기록을 남겼어요.",
                    description = "작은 기록이 특별한 기억이 됩니다.",
                    buttonText = "기록하기",
                    backgroundColor = SoftGreen,
                    accentColor = GreenAccent,
                    icon = Icons.Outlined.Edit,
                    onClick = onRecordClick
                )

            }
        }

    }

}

@Composable
private fun HomeBanner(){
    MemorialBanner(
        height = 280.dp,
        topContent = {
            Text(
                    text = "Memorial",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = "흘러가는 오늘을,\n오래 남을 기억으로.",
                    color = Color(0xFFE0E5F4),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
        },
        bottomContent = {
                Text(
                    text = "오늘도,",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "기억을 기록해보세요.",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )
            },
    )
}

@Composable
private fun MemorialActionCard(
    title: String,
    mainText: String,
    description: String,
    buttonText: String,
    backgroundColor: Color,
    accentColor: Color,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp, shape = RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Color.White.copy(alpha = 0.7f)
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(25.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = DarkText
                )

                Text(
                    text = mainText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    lineHeight = 24.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = description, fontSize = 13.sp, color = Color(0xFF67738D)
        )

        Spacer(modifier = Modifier.height(19.dp))

        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(49.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = buttonText, fontWeight = FontWeight.SemiBold, fontSize = 15.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        homeUiState = HomeUiState(
        recordCount = 7, quizState = QuizState.AVAILABLE
    ), onQuizClick = {}, onRecordClick = {})
}
