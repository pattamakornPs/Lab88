package com.example.korn.lab8;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
  private List<Student> studentsList;
  View itemview;
  public Context context;


  public StudentAdapter(){}
  public StudentAdapter(Context ctxt,List<Student> StudentsList){
      this.context = ctxt;
      this.studentsList = StudentsList;
  }
  public class MyViewHolder extends RecyclerView.ViewHolder {
      TextView txtid,txtname,txttel,txtemail;
      public MyViewHolder(@NonNull View view) {
          super(view);
          txtid = view.findViewById(R.id.textId);
          txtname = view.findViewById(R.id.textname);
          txttel = view.findViewById(R.id.texttel);
          txtemail = view.findViewById(R.id.textemail);
      }
  }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.show_student,parent,false);
      return new StudentAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student st = studentsList.get(position);
        holder.txtid.setText(st.getId());
        holder.txtname.setText(st.getName());
        holder.txtemail.setText(st.getStd_email());
        holder.txttel.setText(st.getTel());
    }


    @Override
    public int getItemCount() {
        return studentsList.size();
    }
}
