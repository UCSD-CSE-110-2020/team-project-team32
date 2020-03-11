package com.example.cse110_project.dialogs;

public interface DialogObserver {
    public void onPositiveResultUpdate(DialogSubject subject);
    public void onNegativeResultUpdate(DialogSubject subject);
}
