package com.example.mainproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.example.mainproject.model.Product;
import com.example.mainproject.model.helper.ProductOpenHelper;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> products;
    Context context;
    private static AdapterView.OnItemClickListener onItemClickListener;

    public ProductAdapter(List<Product> products, Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.products = products;
        this.context = context;
        ProductAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txt_id_product.setText(String.valueOf(product.getId()));
        holder.txt_name_product.setText(product.getName());
        holder.txt_quantity_product.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        return 0;
    }

    public void reloadData(ProductOpenHelper dbHelper) {
        products = dbHelper.getAllProducts();
        notifyDataSetChanged();
    }

    public Product getProduct(int childAdapterPosition) {
        return products.get(childAdapterPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_id_product, txt_name_product, txt_quantity_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_product = itemView.findViewById(R.id.txt_id_product);
            txt_name_product = itemView.findViewById(R.id.txt_name_product);
            txt_quantity_product = itemView.findViewById(R.id.txt_quantity_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

}
