package com.example.workroute.kotlin.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002!\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0002J\u0012\u0010\u001d\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0019H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/example/workroute/kotlin/activities/ChatsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/workroute/kotlin/adapters/AdapterChatsList;", "allUsers", "Ljava/util/ArrayList;", "Lcom/example/workroute/kotlin/activities/ChatsActivity$UserList;", "data", "Lcom/example/workroute/kotlin/model/UserChatModel;", "firebaseUser", "Lcom/google/firebase/auth/FirebaseUser;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "progressCircular", "Landroid/widget/ProgressBar;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "reference", "Lcom/google/firebase/database/DatabaseReference;", "toolbar", "Lcom/google/android/material/appbar/MaterialToolbar;", "txtNull", "Landroid/widget/TextView;", "getData", "", "getUserData", "init", "initListeners", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "DateComparator", "UserList", "app_debug"})
public final class ChatsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.appbar.MaterialToolbar toolbar;
    private androidx.recyclerview.widget.RecyclerView recycler;
    private java.util.ArrayList<com.example.workroute.kotlin.model.UserChatModel> data;
    private com.example.workroute.kotlin.adapters.AdapterChatsList adapter;
    private com.google.firebase.firestore.FirebaseFirestore firestore;
    private com.google.firebase.auth.FirebaseUser firebaseUser;
    private com.google.firebase.database.DatabaseReference reference;
    private java.util.ArrayList<com.example.workroute.kotlin.activities.ChatsActivity.UserList> allUsers;
    private android.widget.ProgressBar progressCircular;
    private android.widget.TextView txtNull;
    private java.util.HashMap _$_findViewCache;
    
    public ChatsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final void init() {
    }
    
    private final void getData() {
    }
    
    private final void getUserData() {
    }
    
    private final void initListeners() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\bH\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b\u00a8\u0006\u001d"}, d2 = {"Lcom/example/workroute/kotlin/activities/ChatsActivity$UserList;", "", "userId", "", "lastMessage", "time", "read", "timeMillis", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getLastMessage", "()Ljava/lang/String;", "getRead", "getTime", "getTimeMillis", "()J", "getUserId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class UserList {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String userId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String lastMessage = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String time = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String read = null;
        private final long timeMillis = 0L;
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.workroute.kotlin.activities.ChatsActivity.UserList copy(@org.jetbrains.annotations.NotNull()
        java.lang.String userId, @org.jetbrains.annotations.NotNull()
        java.lang.String lastMessage, @org.jetbrains.annotations.NotNull()
        java.lang.String time, @org.jetbrains.annotations.NotNull()
        java.lang.String read, long timeMillis) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public UserList(@org.jetbrains.annotations.NotNull()
        java.lang.String userId, @org.jetbrains.annotations.NotNull()
        java.lang.String lastMessage, @org.jetbrains.annotations.NotNull()
        java.lang.String time, @org.jetbrains.annotations.NotNull()
        java.lang.String read, long timeMillis) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getUserId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLastMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getRead() {
            return null;
        }
        
        public final long component5() {
            return 0L;
        }
        
        public final long getTimeMillis() {
            return 0L;
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/example/workroute/kotlin/activities/ChatsActivity$DateComparator;", "Ljava/util/Comparator;", "Lcom/example/workroute/kotlin/model/UserChatModel;", "()V", "compare", "", "o1", "o2", "app_debug"})
    static final class DateComparator implements java.util.Comparator<com.example.workroute.kotlin.model.UserChatModel> {
        
        public DateComparator() {
            super();
        }
        
        @java.lang.Override()
        public int compare(@org.jetbrains.annotations.NotNull()
        com.example.workroute.kotlin.model.UserChatModel o1, @org.jetbrains.annotations.NotNull()
        com.example.workroute.kotlin.model.UserChatModel o2) {
            return 0;
        }
    }
}