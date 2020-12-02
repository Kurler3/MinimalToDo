package com.miguel.minimaltodo;

public interface OnDatabaseChanged {
    void OnTaskInserted();
    void OnTaskRenamed(int taskId);
    void OnTaskReminderDateChanged(int taskId);
    void OnTaskRemoved(int taskId);
    void OnTaskHasReminderChanged(int taskId);
}
