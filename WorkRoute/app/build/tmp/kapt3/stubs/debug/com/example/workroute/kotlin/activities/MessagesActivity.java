package com.example.workroute.kotlin.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020\bH\u0002J(\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010\'\u001a\u00020\bH\u0002J\b\u0010(\u001a\u00020\"H\u0002J\b\u0010)\u001a\u00020\"H\u0002J\b\u0010*\u001a\u00020\"H\u0002J\b\u0010+\u001a\u00020\"H\u0002J\b\u0010,\u001a\u00020\"H\u0002J\u0012\u0010-\u001a\u00020\"2\b\u0010.\u001a\u0004\u0018\u00010/H\u0014J\b\u00100\u001a\u00020\"H\u0014J\b\u00101\u001a\u00020\"H\u0014J\b\u00102\u001a\u00020\"H\u0002J(\u00103\u001a\u00020\"2\u0006\u00104\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010\'\u001a\u00020\b2\u0006\u00105\u001a\u00020\bH\u0002J\u0010\u00106\u001a\u00020\"2\u0006\u0010$\u001a\u00020\bH\u0002J\b\u00107\u001a\u00020\"H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lcom/example/workroute/kotlin/activities/MessagesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapterMessages", "Lcom/example/workroute/kotlin/adapters/AdapterMessages;", "bundle", "Landroid/content/Intent;", "bundlePhoto", "", "data", "Ljava/util/ArrayList;", "Lcom/example/workroute/kotlin/model/MessageModel;", "edText", "Landroid/widget/EditText;", "fabButton", "Landroid/widget/ImageButton;", "firebaseUser", "Lcom/google/firebase/auth/FirebaseUser;", "isInTeChat", "nameUserString", "profilePhoto", "Landroid/widget/ImageView;", "receiverId", "recyclerMessages", "Landroidx/recyclerview/widget/RecyclerView;", "reference", "Lcom/google/firebase/database/DatabaseReference;", "status", "Landroid/widget/TextView;", "toolbar", "Lcom/google/android/material/appbar/MaterialToolbar;", "txtIstyping", "txtUserName", "getSenderName", "", "sender", "message", "s", "getToken", "title", "init", "initListeners", "isInTheChat", "isOnline", "isTyping", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "onStop", "readChatList", "sendNotification", "token", "user", "sendTextMessage", "setData", "app_debug"})
public final class MessagesActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.appbar.MaterialToolbar toolbar;
    private android.widget.ImageView profilePhoto;
    private android.widget.TextView txtUserName;
    private androidx.recyclerview.widget.RecyclerView recyclerMessages;
    private android.widget.EditText edText;
    private android.widget.ImageButton fabButton;
    private com.google.firebase.auth.FirebaseUser firebaseUser;
    private com.google.firebase.database.DatabaseReference reference;
    private com.example.workroute.kotlin.adapters.AdapterMessages adapterMessages;
    private java.lang.String receiverId;
    private android.content.Intent bundle;
    private java.lang.String nameUserString;
    private java.lang.String bundlePhoto;
    private java.util.ArrayList<com.example.workroute.kotlin.model.MessageModel> data;
    private java.lang.String isInTeChat = "";
    private android.widget.TextView txtIstyping;
    private android.widget.TextView status;
    private java.util.HashMap _$_findViewCache;
    
    public MessagesActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    private final void isOnline() {
    }
    
    private final void init() {
    }
    
    /**
     * *************************************************************************************************************
     *              AQUI EMPIEZAN LOS MÉTODOS QUE TIENEN QUE VER CON LOS MENSAJES
     */
    private final void readChatList() {
    }
    
    private final void sendTextMessage(java.lang.String message) {
    }
    
    private final void getToken(java.lang.String sender, java.lang.String receiverId, java.lang.String message, java.lang.String title) {
    }
    
    private final void getSenderName(java.lang.String sender, java.lang.String receiverId, java.lang.String message, java.lang.String s) {
    }
    
    private final void isInTheChat() {
    }
    
    private final void sendNotification(java.lang.String token, java.lang.String message, java.lang.String title, java.lang.String user) {
    }
    
    /**
     * *************************************************************************************************************
     *              AQUI ACABAN LOS MÉTODOS QUE TIENEN QUE VER CON LOS MENSAJES
     */
    private final void setData() {
    }
    
    private final void initListeners() {
    }
    
    private final void isTyping() {
    }
}