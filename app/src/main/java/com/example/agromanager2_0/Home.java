package com.example.agromanager2_0;

import android.annotation.SuppressLint;
import android.content.*;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agromanager2_0.lotes.*;
import com.example.agromanager2_0.database.MyDataBaseHelper;
import com.example.agromanager2_0.aplicaciones.AplicacionActivity;
import com.example.agromanager2_0.cultivos.CultivoActivity;
import com.example.agromanager2_0.labores.LaboresActivity;
import com.example.agromanager2_0.settings.SettingsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.Activity;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private LoteAdapter loteAdapter;
    private final ArrayList<Lote> listaLotes = new ArrayList<>();
    private MyDataBaseHelper miDb;
    private ActivityResultLauncher<Intent> loteActivityLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        miDb = new MyDataBaseHelper(this);

        loteActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        cargarLotes(); // Carga los lotes al volver
                    }
                }
        );
        RecyclerView recyclerViewLotes = findViewById(R.id.recycler_viewLotesHome);
        recyclerViewLotes.setLayoutManager(new LinearLayoutManager(this));

        loteAdapter = new LoteAdapter(listaLotes, miDb, this);
        recyclerViewLotes.setAdapter(loteAdapter);

        cargarLotes(); // Carga los lotes inicialmente

        @SuppressLint("CutPasteId") ImageButton imageButton5 = findViewById(R.id.signomas);
        imageButton5.setOnClickListener(v -> showBottomSheetDialog());

        ImageButton buttonMenuSuperior = findViewById(R.id.menu_button);
        buttonMenuSuperior.setOnClickListener(view2 -> showBottomSheetDialog2());

        ImageButton avatarMiPerfil = findViewById(R.id.avatar_button);
        avatarMiPerfil.setOnClickListener(view3 -> irAMiPerfil());

        @SuppressLint("CutPasteId") FloatingActionButton fab = findViewById(R.id.signomas);
        fab.setContentDescription("Añadir nuevo elemento");

        ImageButton irAMisLabores = findViewById(R.id.imageButton2);
        irAMisLabores.setOnClickListener(view4 -> irAMisLabores());

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> accesoACultivos());

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(view -> accesoAAplicaciones());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLotes(); // Carga los lotes cada vez que se reanuda la actividad
    }

    @SuppressLint("NotifyDataSetChanged")
    private void cargarLotes() {
        listaLotes.clear(); // Limpia la lista antes de cargar
        Cursor cursor = miDb.obtenerLotes();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String nombreLote = cursor.getString(1);
                double hectareas = cursor.getDouble(2);
                double latitud = cursor.getDouble(3);
                double longitud = cursor.getDouble(4);
                LatLng ubicacion = new LatLng(latitud, longitud);

                Lote lote = new Lote(nombreLote, hectareas, ubicacion);
                listaLotes.add(lote);
            }
            loteAdapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
        }
        if (cursor != null) {
            cursor.close(); // Cierra el cursor para evitar fugas de memoria
        }
    }


    public void irAMisLabores(){
        Intent intent = new Intent(this, MisLabores.class);
        startActivity(intent);
    }


    public void irAMiPerfil(){
        Intent intent = new Intent(this, MiPerfil.class);
        startActivity(intent);
    }

    public void accesoACultivos(){
        Intent intent = new Intent(this, MisCultivos.class);
        startActivity(intent);
    }

    public void accesoAAplicaciones(){
        Intent intent = new Intent(this, MisAplicaciones.class);
        startActivity(intent);
    }

    private void showBottomSheetDialog() {
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
            MenuItem fakeItem = new FakeMenuItem(R.id.menu_nueva_labor) ;
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

    private void onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_nuevo_lote) {
            abrirNuevaPantalla("Lotes");
        } else if (id == R.id.menu_nueva_labor) {
            abrirNuevaPantalla("Labores");
        } else if (id == R.id.menu_nuevo_cultivo) {
            abrirNuevaPantalla("Cultivos");
        } else if (id == R.id.menu_nueva_aplicacion) {
            abrirNuevaPantalla("Aplicaciones");
        }
    }

    // Método para abrir la pantalla correspondiente
    private void abrirNuevaPantalla(String pantalla) {
        Intent intent;
        switch (pantalla) {
            case "Lotes":
                intent = new Intent(this, NuevoLoteActivity.class);
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

    }

    private void showBottomSheetDialog2(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        @SuppressLint("InflateParams") View bottomSheetView = LayoutInflater.from(getApplicationContext())
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
            openMenuSuperior();
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.findViewById(R.id.cerrarSesion).setOnClickListener(v ->{

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage(R.string.preguntaUsuario);

            builder.setPositiveButton(R.string.Cancelar, (dialogInterface, i) -> {

            });

            builder.setNegativeButton(R.string.salirConfirmar, (dialogInterface, i) -> cerrarSesionYSalir());

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
        Toast.makeText(Home.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Finalizar todas las actividades y salir de la aplicación
        finishAffinity();  // Cierra todas las actividades y sale de la app
    }

    private void openMenuSuperior() {
        Intent intent = new Intent(this, MenuSuperior.class);
        intent.putExtra("tipo", "Editar Perfil");
        startActivity(intent);
    }

    static class FakeMenuItem implements MenuItem {
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


}