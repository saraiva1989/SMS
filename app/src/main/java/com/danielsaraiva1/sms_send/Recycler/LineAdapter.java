package com.danielsaraiva1.sms_send.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.danielsaraiva1.sms_send.R;
import com.danielsaraiva1.sms_send.SmsModel;

import java.util.ArrayList;
import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private final List<SmsModel> mSms;

    public LineAdapter(ArrayList sms) {
        mSms = sms;
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phone_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder holder, final int position) {
        holder.phone.setText(mSms.get(position).getAddress());
        holder.message.setText(mSms.get(position).getMsg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), v.getContext().getString(R.string.line_position) + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSms != null ? mSms.size() : 0;
    }

    public void updateList(SmsModel sms) {
        insertItem(sms);
    }

    // Método responsável por inserir um novo usuário na lista
    //e notificar que há novos itens.
    private void insertItem(SmsModel sms) {
        mSms.add(sms);
        notifyItemInserted(getItemCount());
    }


}

class LineHolder extends RecyclerView.ViewHolder {

    public TextView phone, message;

    public LineHolder(View itemView) {
        super(itemView);
        phone =  itemView.findViewById(R.id.txNumberPhone);
        message = itemView.findViewById(R.id.txMessage);
    }
}


