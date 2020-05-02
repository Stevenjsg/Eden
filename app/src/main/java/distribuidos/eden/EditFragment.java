package distribuidos.eden;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import Interfaces.IcomunicaFragments;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String option = "nada";
    private Producto p;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, Producto param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(SecondActivity.KEY_ADD, param1);
        args.putSerializable(SecondActivity.KEY_ACT,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            option = getArguments().getString(SecondActivity.KEY_ADD);
            p = (Producto) getArguments().getSerializable(SecondActivity.KEY_ACT);
        }
    }

    private IcomunicaFragments ic;
    private TextInputLayout id, nom, description, cantidad, precio;
    private Button btnAct, btnDel;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_edit, container, false);
        init(vista);
        if (option != null && option.equals("True")) {
            btnAct.setText("Añadir");
            btnDel.setVisibility(View.INVISIBLE);
        }else if(p != null){
            initText(p);
        }
        Botones();
        return vista;
    }

    private  void initText(Producto p){
        id.getEditText().setText(String.valueOf(p.getId()));
        nom.getEditText().setText(p.getNombreProducto());
        description.getEditText().setText(p.getDescripcion());
        cantidad.getEditText().setText(String.valueOf(p.getCantidad()));
        precio.getEditText().setText(String.valueOf(p.getPrecio()));
    }

    private void Botones() {
        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAct.getText().equals("Añadir")) {
                    Producto p = new Producto();
                    try {
                        p.setNombreProducto(nom.getEditText().getText().toString());
                        p.setDescripcion(description.getEditText().getText().toString());
                        p.setCantidad(Integer.valueOf(cantidad.getEditText().getText().toString()));
                        p.setPrecio(Double.valueOf(precio.getEditText().getText().toString()));
                        ic.onClickBtbAdd(p);
                        getActivity().onBackPressed();
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Evite valores vacios!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Producto p = new Producto();
                    p.setId(Integer.valueOf(id.getEditText().getText().toString()));
                    p.setNombreProducto(nom.getEditText().getText().toString());
                    p.setDescripcion(description.getEditText().getText().toString());
                    p.setCantidad(Integer.valueOf(cantidad.getEditText().getText().toString()));
                    p.setPrecio(Double.valueOf(precio.getEditText().getText().toString()));
                    ic.onClickBtbUpdate(p);
                    getActivity().onBackPressed();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto p = new Producto();
                p.setId(Integer.valueOf(id.getEditText().getText().toString()));
                ic.onClickBtbDelete(p);
                getActivity().onBackPressed();
            }
        });
    }


    private void init(View vista) {
        id = vista.findViewById(R.id.txtID);
        id.setEnabled(false);
        nom = vista.findViewById(R.id.txtPro);
        description = vista.findViewById(R.id.txtdescripcion);
        cantidad = vista.findViewById(R.id.txtcantidad);
        precio = vista.findViewById(R.id.txtprecio);
        btnAct = vista.findViewById(R.id.btn_update);
        btnDel = vista.findViewById(R.id.btn_delete);
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
        if (context instanceof Activity) {
            activity = (Activity) context;
            ic = (IcomunicaFragments) activity;
        }

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    }
}
