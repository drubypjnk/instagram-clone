package com.example.prm392_project.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.R;

public class CommentFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_component_fragment, container, false);

        /** Get data from view*/
        TextView author = view.findViewById(R.id.author);
        ImageView avatar = view.findViewById(R.id.avatar);
        TextView content = view.findViewById(R.id.post_content);

        /** Decode base64 to bitmap */
        byte[] decodedAvatar = Base64.decode(getArguments().get(CommonConstant.AVATAR).toString(), Base64.DEFAULT);
        Bitmap avatarBitmap = BitmapFactory.decodeByteArray(decodedAvatar, 0, decodedAvatar.length);

        /** Set data to view*/
        avatar.setImageBitmap(avatarBitmap);
        author.setText(getArguments().get(CommonConstant.AUTHOR).toString());
        content.setText(getArguments().get(CommonConstant.CONTENT).toString());

        return view;
    }
}
