package nguyenquyen.quyen.blogviettruyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TrangLamViecAdapter extends BaseAdapter {

    private TrangLamViecMain context;
    private int layout;
    private List<TrangLamViec> trangLamViecList;

    public TrangLamViecAdapter(TrangLamViecMain context, int layout, List<TrangLamViec> trangLamViecList) {
        this.context = context;
        this.layout = layout;
        this.trangLamViecList = trangLamViecList;
    }

    @Override
    public int getCount() {
        return trangLamViecList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvTen;
        ImageView imgSua,imgXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);

            holder.tvTen=(TextView) view.findViewById(R.id.tvTen);
            holder.imgSua=(ImageView) view.findViewById(R.id.imgSua);
            holder.imgXoa=(ImageView) view.findViewById(R.id.imgXoa);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        TrangLamViec trangLamViec=trangLamViecList.get(i);
        holder.tvTen.setText(trangLamViec.getTen());

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Sửa", Toast.LENGTH_SHORT).show();
                context.DiaLogUpdate(trangLamViec.getTen(),trangLamViec.getId());
            }
        });

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DiaLogDelete(trangLamViec.getTen(),trangLamViec.getId());
            }
        });

        return view;
    }
}
