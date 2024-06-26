package com.castorpos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerViewHolder> {
    private List<String> servers;
    private OnServerClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION; // Track the selected position

    public ServerAdapter(List<String> servers, OnServerClickListener listener) {
        this.servers = servers;
        this.listener = listener;
    }

    @Override
    public ServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_list_item, parent, false);
        return new ServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServerViewHolder holder, int position) {
        String server = servers.get(position);
        holder.serverName.setText(server);

        // Highlight the selected server
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(0xFFADD8E6); // Use light blue color directly
        } else {
            holder.itemView.setBackgroundColor(0x00000000); // Transparent color
        }

        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            listener.onServerSelected(server);
        });

        holder.deleteServerButton.setOnClickListener(v -> {
            servers.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, servers.size());
            listener.onServerDeleted(server);
        });
    }

    @Override
    public int getItemCount() {
        return servers.size();
    }

    public static class ServerViewHolder extends RecyclerView.ViewHolder {
        TextView serverName;
        Button deleteServerButton;
        LinearLayout itemView;

        public ServerViewHolder(View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.server_name);
            deleteServerButton = itemView.findViewById(R.id.delete_server_button);
            this.itemView = (LinearLayout) itemView;
        }
    }

    public interface OnServerClickListener {
        void onServerSelected(String server);
        void onServerDeleted(String server);
    }
}
