package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.presenter.UserInfoPresenter;
import com.chrisyoung.huajiangapp.uitils.ImageUtil;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IMineInfoView;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment implements IMineInfoView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.mine_icon)
    CircleImageView mineIcon;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mineGroupListView)
    QMUIGroupListView mineGroupListView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String uId;
    private String token;
    private UserInfoPresenter userInfoPresenter;

    private OnFragmentInteractionListener mListener;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uId = getArguments().getString(ARG_PARAM1);
            token = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        String name= (String) SharedPreferenceUtil.get(this.getContext(),UserConfig.USER_NAME,"");
        userInfoPresenter=new UserInfoPresenter(this,getContext());
        userInfoPresenter.initView(uId);
        mineName.setText(name);
        initGroupListView();
        return view;
    }


    private void initGroupListView() {
        QMUICommonListItemView itemSetKinds = mineGroupListView.createItemView(
                "类别设置");
        itemSetKinds.setOrientation(QMUICommonListItemView.VERTICAL);
        itemSetKinds.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUICommonListItemView itemMine = mineGroupListView.createItemView("我的信息");
        itemMine.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView itemSync = mineGroupListView.createItemView("同步数据");
        itemSync.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if (text.equals("我的信息")) {
                        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                        intent.putExtra("uId",uId);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    } else if (text.equals("类别设置")) {
                        Intent intent = new Intent(getActivity(), KindsActivity.class);
                        intent.putExtra("uId",uId);
                        intent.putExtra("token",token);
                        getContext().startActivity(intent);
                        getActivity().finish();
                    } else{
                        mListener.sycnData();
                    }

                }
            }
        };


        QMUIGroupListView.newSection(getContext())
                .addItemView(itemMine, onClickListener)
                .addItemView(itemSetKinds, onClickListener)
                .addItemView(itemSync, onClickListener)
                .addTo(mineGroupListView);


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initView(CUser user) {
        if(user==null){
            return;
        }
        if(user.getuPhoto().length()>2){
            File imageFile=new File(user.getuPhoto());
            Uri uri=FileProvider.getUriForFile(Objects.requireNonNull(getContext()),ImageUtil.FILE_PROVIDER,imageFile);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
                mineIcon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void jum2LoginActivity() {

    }

    @Override
    public void showResult(String result) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void sycnData();
    }
}
