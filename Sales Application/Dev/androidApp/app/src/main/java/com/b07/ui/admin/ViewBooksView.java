package com.b07.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;

import java.sql.SQLException;

public class ViewBooksView extends AppCompatActivity {
  Toast message;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_books);
    Intent intent = getIntent();
    AdminInterface adminInterface = (AdminInterface) intent.getSerializableExtra("ADMIN_INTERFACE");
    TextView output = (TextView) findViewById(R.id.txtViewBooksOutput);
    output.setText(booksView(this, adminInterface));
  }

  protected String booksView(Context context, AdminInterface adminInterface) {
    String books = "";
    try {
      books = adminInterface.viewBooks(context);
    } catch (SQLException e) {
      message = Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT);
      message.show();
    }
    return books;
  }
}
