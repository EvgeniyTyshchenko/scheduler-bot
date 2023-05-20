package com.example.telegrambot.timer;

import com.example.telegrambot.entity.NotificationTask;
import com.example.telegrambot.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationTaskTimerTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private NotificationTaskRepository notificationTaskRepository;
    @InjectMocks
    private NotificationTaskTimer notificationTaskTimer;

    @Test
    void shouldSendingNotification() {
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setMessage("Тест");
        notificationTask.setChatId(123);
        notificationTask.setNotificationDateTime(LocalDateTime.now());

        when(notificationTaskRepository.findAllByNotificationDateTime(any()))
                .thenReturn(Collections.singletonList(notificationTask));
        notificationTaskTimer.task();

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals(actual.getParameters().get("chat_id"), notificationTask.getChatId());
        assertEquals(actual.getParameters().get("text"), "Вы просили напомнить о задаче: " + notificationTask.getMessage());
    }
}