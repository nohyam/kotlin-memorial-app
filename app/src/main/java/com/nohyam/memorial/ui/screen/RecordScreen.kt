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
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohyam.memorial.data.entity.RecordEntity
import com.nohyam.memorial.ui.screen.component.MemorialBackButton
import com.nohyam.memorial.ui.screen.component.MemorialBanner
import com.nohyam.memorial.ui.theme.Background
import com.nohyam.memorial.ui.theme.DarkText
import com.nohyam.memorial.ui.theme.GreenAccent
import com.nohyam.memorial.ui.theme.NavyButton
import com.nohyam.memorial.ui.theme.PurpleAccent
import com.nohyam.memorial.ui.theme.SoftGreen
import com.nohyam.memorial.ui.theme.SoftPurple
import com.nohyam.memorial.ui.theme.SubText


@Composable
fun RecordScreen(
    records: List<RecordEntity>,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: (RecordEntity) -> Unit,
    onDeleteClick: (RecordEntity) -> Unit
) {
    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = NavyButton,
                contentColor = Color.White,
                modifier = Modifier.shadow(
                    elevation = 8.dp,
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "기록 추가",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RecordBanner(onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 28.dp,
                            topEnd = 28.dp
                        )
                    )
                    .background(Background)
                    .padding(top = 24.dp)
            ) {
                if (records.isEmpty()) {
                    RecordEmptyState(
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "오늘 ${records.size}개의 기록이 있어요.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 20.dp
                        )
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 100.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = records,
                            key = { it.id }
                        ) { record ->

                            val isPurple =
                                records.indexOf(record) % 2 == 0

                            RecordCard(
                                record = record,
                                backgroundColor = if (isPurple) {
                                    SoftPurple
                                } else {
                                    SoftGreen
                                },
                                accentColor = if (isPurple) {
                                    PurpleAccent
                                } else {
                                    GreenAccent
                                },
                                onEditClick = {
                                    onEditClick(record)
                                },
                                onDeleteClick = {
                                    onDeleteClick(record)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecordBanner(
    onBackClick: () -> Unit
) {
    MemorialBanner(
        height = 240.dp,
        topContent = {
            MemorialBackButton { onBackClick() }
        },
        bottomContent = {
            Text(
                text = "오늘의 기록",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "오늘 기억하고 싶은 내용을\n질문과 답변으로 남겨보세요.",
                color = Color(0xFFE0E5F4),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    )
}


@Composable
private fun RecordCard(
    record: RecordEntity,
    backgroundColor: Color,
    accentColor: Color,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Color.White.copy(alpha = 0.7f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(23.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = record.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                lineHeight = 22.sp
            )

            Text(
                text = record.answer,
                fontSize = 13.sp,
                color = SubText,
                lineHeight = 20.sp
            )
        }

        Row {
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "수정",
                    tint = NavyButton,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "삭제",
                    tint = NavyButton,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun RecordEmptyState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(
            24.dp
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(SoftPurple),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                    contentDescription = null,
                    tint = PurpleAccent,
                    modifier = Modifier.size(54.dp)
                )
            }

            Text(
                text = "아직 작성한 기록이 없어요.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "오늘 기억하고 싶은 내용을\n질문과 답변으로 남겨보세요.",
                fontSize = 14.sp,
                color = SubText,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecordScreenPreview() {
    RecordScreen(
        records = listOf(

        ),
        onAddClick = {},
        onBackClick = {},
        onEditClick = {},
        onDeleteClick = {}
    )
}
