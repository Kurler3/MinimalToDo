package com.miguel.minimaltodo;

public interface OnDatabaseChanged {
    void OnTaskInserted();
    void OnTaskRenamed();
    void OnTaskReminderDateChanged();
    void OnTaskRemoved();
}
