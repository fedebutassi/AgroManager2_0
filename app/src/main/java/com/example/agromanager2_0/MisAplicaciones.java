package com.example.agromanager2_0;

import static com.example.agromanager2_0.lotes.NuevoLoteActivity.listaLotes;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agromanager2_0.aplicaciones.Aplicacion;
import com.example.agromanager2_0.aplicaciones.AplicacionAdapter;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.example.agromanager2_0.aplicaciones.AplicacionActivity;
import com.example.agromanager2_0.cultivos.CultivoActivity;
import com.example.agromanager2_0.labores.Labor;
import com.example.agromanager2_0.labores.LaborAdapter;
import com.example.agromanager2_0.labores.LaboresActivity;
import com.example.agromanager2_0.lotes.Lote;
import com.example.agromanager2_0.lotes.LoteAdapter;
import com.example.agromanager2_0.lotes.NuevoLoteActivity;
import com.example.agromanager2_0.settings.SettingsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.Activity;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MisAplicaciones extends AppCompatActivity {

    private final ArrayList<Aplicacion> listaAplicaciones = new ArrayList<>();
    private MyDataBaseHelper miDb;
    private ActivityResultLauncher<Intent> loteActivityLauncher;
    private AplicacionAdapter aplicacionAdapter; // Asegúrate de que el adaptador está correctamente definido
    private final List<Aplicacion> aplicaciones = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_aplicaciones   );

        miDb = new MyDataBaseHelper(this);

        loteActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        cargarAplicaciones();
                    }
                }
        );
        RecyclerView recyclerViewLabores = findViewById(R.id.recycler_viewAplicacionesHome);
        recyclerViewLabores.setLayoutManager(new LinearLayoutManager(this));

        aplicacionAdapter = new AplicacionAdapter(listaAplicaciones);
        recyclerViewLabores.setAdapter(aplicacionAdapter);

        cargarAplicaciones(); // Carga los lotes inicialmente

        ImageButton imageButton5 = findViewById(R.id.signomas);
        imageButton5.setOnClickListener(v -> showBottomSheetDialog(v));

        ImageButton buttonMenuSuperior = findViewById(R.id.menu_button);
        buttonMenuSuperior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view2){
                showBottomSheetDialog2(view2);
            }
        });

        ImageButton avatarMiPerfil = findViewById(R.id.avatar_button);
        avatarMiPerfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view3){
                irAMiPerfil();
            }
        });

        FloatingActionButton fab = findViewById(R.id.signomas);
        fab.setContentDescription("Añadir nuevo elemento");

        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view4){
                accesoALotes();
            }
        });

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accesoACultivos();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarAplicaciones(); // Carga los lotes cada vez que se reanuda la actividad
    }

    private void cargarAplicaciones() {
        listaAplicaciones.clear();
        Cursor cursor = null;
        try {
            cursor = miDb.obtenerAplicaciones();
            if (cursor != null && cursor.getCount() > 0) {
                logColumnasCursor(cursor);

                while (cursor.moveToNext()) {
                    logColumnasCursor(cursor);
                    String nombreAplicacion = cursor.getString(1);
                    String fechaAplicacion = cursor.getString(3);
                    String areaCubierta = cursor.getString(5);
                    String descripcionAplicacion = cursor.getString(6);
                        Aplicacion aplicacion = new Aplicacion(nombreAplicacion, fechaAplicacion,areaCubierta,descripcionAplicacion);
                    listaAplicaciones.add(aplicacion);
                }
                aplicacionAdapter.notifyDataSetChanged();
            } else {
                Log.e("Cursor Error", "No se encontraron labores.");
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Asegurarse de cerrar el cursor
            }
        }
    }
    private void logColumnasCursor(Cursor cursor) {
        String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            Log.d("Cursor Column Info", "Posición: " + i + ", Nombre: " + columnNames[i]);
        }
    }



    public void irAMiPerfil(){
        Intent intent = new Intent(this, MiPerfil.class);
        startActivity(intent);
    }

    private void showBottomSheetDialog(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_menu, null);

        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetView.findViewById(R.id.nuevoLote).setOnClickListener(v -> {
            MenuItem fakeItem = new FakeMenuItem(R.id.menu_nuevo_lote);
            onMenuItemClick(fakeItem);
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevaLabor).setOnClickListener(v -> {
            MenuItem fakeItem = new FakeMenuItem(R.id.menu_nueva_labor);
            onMenuItemClick(fakeItem);
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevaAplicacion).setOnClickListener(v -> {
            MenuItem fakeItem = new FakeMenuItem(R.id.menu_nueva_aplicacion);
            onMenuItemClick(fakeItem);
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.nuevoCultivo).setOnClickListener(v -> {
            MenuItem fakeItem = new FakeMenuItem(R.id.menu_nuevo_cultivo);
            onMenuItemClick(fakeItem);
            bottomSheetDialog.dismiss();
        });


        bottomSheetDialog.show();
    }

    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_nuevo_lote) {
            abrirNuevaPantalla("Lotes");
            return true;
        } else if (id == R.id.menu_nueva_labor) {
            abrirNuevaPantalla("Labores");
            return true;
        } else if (id == R.id.menu_nuevo_cultivo) {
            abrirNuevaPantalla("Cultivos");
            return true;
        } else if (id == R.id.menu_nueva_aplicacion) {
            abrirNuevaPantalla("Aplicaciones");
            return true;
        } else {
            return false;
        }
    }

    // Método para abrir la pantalla correspondiente
    private void abrirNuevaPantalla(String pantalla) {
        Intent intent;
        switch (pantalla) {
            case "Lotes":
                intent = new Intent(this, NuevoLoteActivity.class);
                // En lugar de startActivityForResult, usamos el launcher registrado
                loteActivityLauncher.launch(intent);
                break;
            case "Labores":
                intent = new Intent(this, LaboresActivity.class);
                startActivity(intent);
                break;
            case "Aplicaciones": // Agregar este caso
                intent = new Intent(this, AplicacionActivity.class);
                startActivity(intent);
                break;
            case "Cultivos":
                intent = new Intent(this, CultivoActivity.class);
                startActivity(intent);
                break;
            default:
                throw new IllegalArgumentException("Pantalla no reconocida: " + pantalla);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            boolean loteCreado = true; // Indica que se creo un lote
        }
    }


    private void openFormActivity(String tipo) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }
    /*Implementacion de menu desplegable con botones
     *   "Configuracion"
     *   "Editar perfil"
     *   "Cerrar sesion"*/
    private void showBottomSheetDialog2(View view2){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.menusuperior, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetView.findViewById(R.id.configuracion).setOnClickListener(v ->{
            // Crear un intent para abrir la actividad de configuración
            Intent intent = new Intent(v.getContext(), SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);

            // Cerrar el BottomSheet
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.editarPerfil).setOnClickListener(v ->{
            openMenuSuperior("Editar Perfil");
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.cerrarSesion).setOnClickListener(v ->{

            AlertDialog.Builder builder = new AlertDialog.Builder(MisAplicaciones.this);
            builder.setMessage(R.string.preguntaUsuario);

            builder.setPositiveButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setNegativeButton(R.string.salirConfirmar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cerrarSesionYSalir();
                }
            });

            builder.show();
        });


        bottomSheetDialog.show();
    }

    private void cerrarSesionYSalir() {
        // Eliminar los datos de sesión (por ejemplo, en SharedPreferences)
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Limpiar todos los datos de sesión
        editor.apply();

        // Mostrar un mensaje que la sesión se ha cerrado
        Toast.makeText(MisAplicaciones.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Finalizar todas las actividades y salir de la aplicación
        finishAffinity();  // Cierra todas las actividades y sale de la app
    }

    private void openMenuSuperior(String tipo) {
        Intent intent = new Intent(this, MenuSuperior.class);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }

    class FakeMenuItem implements MenuItem {
        private final int id;

        public FakeMenuItem(int id) {
            this.id = id;
        }

        @Override
        public int getItemId() {
            return id;
        }

        // Métodos restantes de la interfaz MenuItem que no necesitamos implementar para este caso
        @Override
        public boolean isEnabled() { return false; }

        @Override
        public boolean hasSubMenu() {
            return false;
        }

        @Nullable
        @Override
        public SubMenu getSubMenu() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setOnMenuItemClickListener(@Nullable OnMenuItemClickListener onMenuItemClickListener) {
            return null;
        }

        @Nullable
        @Override
        public ContextMenu.ContextMenuInfo getMenuInfo() {
            return null;
        }

        @Override
        public void setShowAsAction(int i) {

        }

        @NonNull
        @Override
        public MenuItem setShowAsActionFlags(int i) {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setActionView(@Nullable View view) {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setActionView(int i) {
            return null;
        }

        @Nullable
        @Override
        public View getActionView() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setActionProvider(@Nullable ActionProvider actionProvider) {
            return null;
        }

        @Nullable
        @Override
        public ActionProvider getActionProvider() {
            return null;
        }

        @Override
        public boolean expandActionView() {
            return false;
        }

        @Override
        public boolean collapseActionView() {
            return false;
        }

        @Override
        public boolean isActionViewExpanded() {
            return false;
        }

        @NonNull
        @Override
        public MenuItem setOnActionExpandListener(@Nullable OnActionExpandListener onActionExpandListener) {
            return null;
        }

        @Override
        public MenuItem setEnabled(boolean enabled) { return this; }
        @Override
        public int getGroupId() { return 0; }
        @Override
        public int getOrder() { return 0; }

        @NonNull
        @Override
        public MenuItem setTitle(@Nullable CharSequence charSequence) {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setTitle(int i) {
            return null;
        }

        @Nullable
        @Override
        public CharSequence getTitle() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setTitleCondensed(@Nullable CharSequence charSequence) {
            return null;
        }

        @Nullable
        @Override
        public CharSequence getTitleCondensed() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setIcon(@Nullable Drawable drawable) {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setIcon(int i) {
            return null;
        }

        @Nullable
        @Override
        public Drawable getIcon() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setIntent(@Nullable Intent intent) {
            return null;
        }

        @Nullable
        @Override
        public Intent getIntent() {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setShortcut(char c, char c1) {
            return null;
        }

        @NonNull
        @Override
        public MenuItem setNumericShortcut(char c) {
            return null;
        }

        @Override
        public char getNumericShortcut() {
            return 0;
        }

        @NonNull
        @Override
        public MenuItem setAlphabeticShortcut(char c) {
            return null;
        }

        @Override
        public char getAlphabeticShortcut() {
            return 0;
        }

        @NonNull
        @Override
        public MenuItem setCheckable(boolean b) {
            return null;
        }

        @Override
        public boolean isCheckable() {
            return false;
        }

        @NonNull
        @Override
        public MenuItem setChecked(boolean b) {
            return null;
        }

        @Override
        public boolean isChecked() {
            return false;
        }

        @Override
        public MenuItem setVisible(boolean visible) { return this; }
        @Override
        public boolean isVisible() { return false; }
        // ... otros métodos omitidos por simplicidad
    }

    public void accesoALotes(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void accesoACultivos(){
        Intent intent = new Intent(this, MisCultivos.class);
        startActivity(intent);
    }
}