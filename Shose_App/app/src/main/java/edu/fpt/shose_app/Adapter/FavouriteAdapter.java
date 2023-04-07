//
//
//package edu.fpt.shose_app.Adapter;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import edu.fpt.shose_app.Model.Product;
//import edu.fpt.shose_app.R;
//
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myviewHolder> {
//    private Context context;
//    private ArrayList<Product> favouriteProductList;
//
//    public FavouriteAdapter(Context context, ArrayList<Product> favouriteProductList) {
//        this.context = context;
//        this.favouriteProductList = favouriteProductList;
//    }
//
//    @NonNull
//    @Override
//    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
//        return new myviewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
//        Product product = favouriteProductList.get(position);
//        Log.d("hh", "onBindViewHolder: " + product);
//        // bind product data to views
//        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(holder.itemproduct_img);
//        holder.itemproduct_name.setText(product.getName());
//        holder.itemproduct_price.setText(product.getPrice() + "");
//        holder.itemClickFav.setImageResource(R.drawable.baseline_favorite_24);
//    }
//
//    @Override
//    public int getItemCount() {
////        return favouriteProductList.size();
//        if (favouriteProductList == null) {
//            return 0;
//        }
//        return favouriteProductList.size();
//    }
//
//    public class myviewHolder extends RecyclerView.ViewHolder {
//        ImageView itemproduct_img;
//        ImageView itemClickFav;
//        TextView itemproduct_name,itemproduct_price;
//
//        public myviewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemproduct_img = itemView.findViewById(R.id.item_product_image);
//            itemproduct_name = itemView.findViewById(R.id.item_product_name);
//            itemproduct_price = itemView.findViewById(R.id.item_product_price);
//            itemClickFav = itemView.findViewById(R.id.itemClickFav);
//        }
//    }
//}
//
//





//
//package edu.fpt.shose_app.Adapter;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import edu.fpt.shose_app.Model.Product;
//import edu.fpt.shose_app.R;
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myviewHolder> {
//    private Context context;
//    private ArrayList<Product> favouriteProductList;
//    private OnItemClickListener listener;
//
//    public FavouriteAdapter(Context context, ArrayList<Product> favouriteProductList) {
//        this.context = context;
//        this.favouriteProductList = favouriteProductList;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
//        return new myviewHolder(view);
//    }
//
//    @SuppressLint("RecyclerView")
//    @Override
//    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
//        Product product = favouriteProductList.get(position);
//        // bind product data to views
//        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(holder.itemproduct_img);
//        holder.itemproduct_name.setText(product.getName());
//        holder.itemproduct_price.setText(product.getPrice() + "");
//
//        // set click listener on favourite icon
//        holder.itemClickFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // remove current product from favourite list
//                favouriteProductList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, getItemCount());
//
//                // notify listener that a product has been removed from favourite list
//                if (listener != null) {
//                    listener.onItemRemovedFromFavourite(product);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (favouriteProductList == null) {
//            return 0;
//        }
//        return favouriteProductList.size();
//    }
//
//    public class myviewHolder extends RecyclerView.ViewHolder {
//        ImageView itemproduct_img;
//        ImageView itemClickFav;
//        TextView itemproduct_name, itemproduct_price;
//
//        public myviewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemproduct_img = itemView.findViewById(R.id.item_product_image);
//            itemproduct_name = itemView.findViewById(R.id.item_product_name);
//            itemproduct_price = itemView.findViewById(R.id.item_product_price);
//            itemClickFav = itemView.findViewById(R.id.itemClickFav);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemRemovedFromFavourite(Product product);
//    }
//
//}


///code hoàn chỉnh
//package edu.fpt.shose_app.Adapter;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import edu.fpt.shose_app.Model.Product;
//import edu.fpt.shose_app.R;
//
//
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myviewHolder> {
//    private Context context;
//    private ArrayList<Product> favouriteProductList;
//    private ArrayList<Product> favouriteList;
//
//    public FavouriteAdapter(Context context, ArrayList<Product> favouriteProductList) {
//        this.context = context;
//        this.favouriteProductList = favouriteProductList;
//        this.favouriteList = new ArrayList<>();
//        for (Product product : favouriteProductList) {
//            if (product.isFavourite()) {
//                favouriteList.add(product);
//            }
//        }
//    }
//
//    @NonNull
//    @Override
//    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
//        return new myviewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
//        Product product = favouriteList.get(position);
//        // bind product data to views
//        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(holder.itemproduct_img);
//        holder.itemproduct_name.setText(product.getName());
//        holder.itemproduct_price.setText(product.getPrice() + "");
//        holder.itemClickFav.setImageResource(R.drawable.baseline_favorite_24);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (favouriteList == null) {
//            return 0;
//        }
//        return favouriteList.size();
//    }
//
//    public class myviewHolder extends RecyclerView.ViewHolder {
//        ImageView itemproduct_img;
//        ImageView itemClickFav;
//        TextView itemproduct_name,itemproduct_price;
//
//        public myviewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemproduct_img = itemView.findViewById(R.id.item_product_image);
//            itemproduct_name = itemView.findViewById(R.id.item_product_name);
//            itemproduct_price = itemView.findViewById(R.id.item_product_price);
//            itemClickFav = itemView.findViewById(R.id.itemClickFav);
//        }
//    }
//        public interface OnItemClickListener {
//        void onItemRemovedFromFavourite(Product product);
//    }
//}




package edu.fpt.shose_app.Adapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myviewHolder> {
    private Context context;
    private ArrayList<Product> favouriteProductList;
    private ArrayList<Product> favouriteList;
    private OnItemRemovedFromFavouriteListener listener;
    public FavouriteAdapter(Context context, ArrayList<Product> favouriteProductList) {
        this.context = context;
        this.favouriteProductList = favouriteProductList;
        this.favouriteList = new ArrayList<>();


        for (Product product : favouriteProductList) {
            if (product.isFavourite()) {
                favouriteList.add(product);
            }
        }
    }

    public interface OnItemRemovedFromFavouriteListener {
        void onItemRemovedFromFavourite(Product product);
    }

    // Define the setter method for the listener
    public void setOnItemRemovedFromFavouriteListener(OnItemRemovedFromFavouriteListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
        return new myviewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        Product product = favouriteList.get(position);
        // bind product data to views
        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(holder.itemproduct_img);
        holder.itemproduct_name.setText(product.getName());
        holder.itemproduct_price.setText(product.getPrice() + "");
        holder.itemClickFav.setImageResource(R.drawable.baseline_favorite_24);
        // set click listener on favourite icon
        holder.itemClickFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này khỏi danh sách yêu thích?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xoá sản phẩm khỏi danh sách yêu thích

                                Product product = favouriteList.get(position);
                                    favouriteList.remove(product);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
//                                    SharedPreferences prefs = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = prefs.edit();
//                                    editor.remove(String.valueOf(product.getId()));
//                                    editor.apply();



                                // Thông báo sự kiện xoá item đến activity/fragment sử dụng adapter
                                if (listener != null) {
                                    listener.onItemRemovedFromFavourite(product);
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (favouriteList == null) {
            return 0;
        }
        return favouriteList.size();
    }


    public class myviewHolder extends RecyclerView.ViewHolder {
        ImageView itemproduct_img;
        ImageView itemClickFav;
        TextView itemproduct_name,itemproduct_price;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img = itemView.findViewById(R.id.item_product_image);
            itemproduct_name = itemView.findViewById(R.id.item_product_name);
            itemproduct_price = itemView.findViewById(R.id.item_product_price);
            itemClickFav = itemView.findViewById(R.id.itemClickFav);
        }
    }
    public interface OnItemClickListener {
        void onItemRemovedFromFavourite(Product product);

    }
    public void removeItem(int position) {
        favouriteProductList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}


//package edu.fpt.shose_app.Adapter;
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//import edu.fpt.shose_app.Model.Product;
//import edu.fpt.shose_app.R;
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.myviewHolder> {
//    private Context context;
//    private ArrayList<Product> favouriteProductList;
//    private OnItemClickListener listener;
//
//    public FavouriteAdapter(Context context, ArrayList<Product> favouriteProductList) {
//        this.context = context;
//        this.favouriteProductList = favouriteProductList;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false);
//        return new myviewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
//        Product product = favouriteProductList.get(position);
//        // bind product data to views
//        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(holder.itemproduct_img);
//        holder.itemproduct_name.setText(product.getName());
//        holder.itemproduct_price.setText(product.getPrice() + "");
//
//        // set click listener on favourite icon
//        holder.itemClickFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // remove current product from favourite list
//                favouriteProductList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, getItemCount());
//
//                // notify listener that a product has been removed from favourite list
//                if (listener != null) {
//                    listener.onItemRemovedFromFavourite(product);
//                }
//            }
//        });
//
//        // check if the product is favourite and set the favourite icon accordingly
//        if (product.isFavourite()) {
//            holder.itemClickFav.setImageResource(R.drawable.baseline_favorite_24);
//        } else {
//            holder.itemClickFav.setImageResource(R.drawable.baseline_favorite_border_24);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (favouriteProductList == null) {
//            return 0;
//        }
//        return favouriteProductList.size();
//    }
//
//    public class myviewHolder extends RecyclerView.ViewHolder {
//        ImageView itemproduct_img;
//        ImageView itemClickFav;
//        TextView itemproduct_name,itemproduct_price;
//
//        public myviewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemproduct_img = itemView.findViewById(R.id.item_product_image);
//            itemproduct_name = itemView.findViewById(R.id.item_product_name);
//            itemproduct_price = itemView.findViewById(R.id.item_product_price);
//            itemClickFav = itemView.findViewById(R.id.itemClickFav);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemRemovedFromFavourite(Product product);
//    }
//}
