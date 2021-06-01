package com.example.bassametproject;


import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

// classes needed to initialize map
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

// classes needed to add the location component
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

import org.jetbrains.annotations.NotNull;

public class MapBoxActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener
      {
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";


    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    Button Button3D;
    ImageButton startButton;
    private boolean _3d;
    TextView textDestination,textLocation ;
    ProgressBar mapboxProgressBar;
    private BuildingPlugin buildingPlugin;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    //search variables
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    //private CarmenFeature home;
    //private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    /**
     * Display {@link SymbolLayer} icons on the map.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));


// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map_box);
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        Button3D = findViewById(R.id.Button3D);
       //textDestination=findViewById(R.id.TextDestination);
       textLocation=findViewById(R.id.Textlocation);
        mapboxProgressBar=findViewById(R.id.progressBar);


        Button3D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraPosition position = null;

                if (_3d == false) {
                    Button3D.setBackgroundResource(R.drawable._2dicon);
                    _3d = true;
                    position = new CameraPosition.Builder()
                            .bearing(180) // Rotate the camera
                            .tilt(59) // Set the camera tilt
                            .build(); // Creates a CameraPosition from the builder
                } else if (_3d == true) {
                    Button3D.setBackgroundResource(R.drawable._3dicon);
                    _3d = false;
                    position = new CameraPosition.Builder()
                            .bearing(0) // Rotate the camera
                            .tilt(0) // Set the camera tilt
                            .build(); // Creates a CameraPosition from the builder
                }

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);

            }
        });
    }
    /*private void initSearchFab() {
        textDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(MapBoxActivity.this);
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }
    private void addUserLocations() {
        home = CarmenFeature.builder().text("Mapbox SF Office")
                .geometry(Point.fromLngLat(-122.3964485, 37.7912561))
                .placeName("50 Beale St, San Francisco, CA")
                .id("mapbox-sf")
                .properties(new JsonObject())
                .build();

        work = CarmenFeature.builder().text("Mapbox DC Office")
                .placeName("740 15th Street NW, Washington DC")
                .geometry(Point.fromLngLat(-77.0338348, 38.899750))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();
    }
    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[] {0f, -8f})
        ));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[] {Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(20)
                                    .build()), 4000);
                }
            }
        }
    }*/

          @Override
          public void onMapReady(@NonNull final MapboxMap mapboxMap) {
              this.mapboxMap = mapboxMap;
              mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {

                  @Override
                  public void onStyleLoaded(@NonNull Style style) {
                      enableLocationComponent(style);

                      buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);
                      buildingPlugin.setMinZoomLevel(15f);
                      buildingPlugin.setVisibility(true);

                      addDestinationIconSymbolLayer(style);

                      mapboxMap.addOnMapClickListener(MapBoxActivity.this);
                      startButton = findViewById(R.id.startButton);
                      startButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              boolean simulateRoute = true;

                              CameraPosition initialPosition = new CameraPosition.Builder()
                                      .zoom(25.5)
                                      .build();

                              NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                      .directionsRoute(currentRoute).initialMapCameraPosition(initialPosition).build();

// Call this method with Context from within an Activity
                              NavigationLauncher.startNavigation(MapBoxActivity.this, options);
                          }
                      });
                  }
              });
          }


          private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
              loadedMapStyle.addImage("destination-icon-id",
                      BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
              GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
              loadedMapStyle.addSource(geoJsonSource);
              SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
              destinationSymbolLayer.withProperties(
                      iconImage("destination-icon-id"),
                      iconAllowOverlap(true),
                      iconIgnorePlacement(true)
              );
              loadedMapStyle.addLayer(destinationSymbolLayer);
          }

          @SuppressWarnings( {"MissingPermission"})
          @Override
          public boolean onMapClick(@NonNull LatLng point) {

              //Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
              Point destinationPoint = null;
              if(adapterShopsHome.shop1!=null){
                  destinationPoint = Point.fromLngLat(adapterShopsHome.shop1.getLongitude(), adapterShopsHome.shop1.getLatitude());

              }else if(adapterShops.shop!=null){
                  destinationPoint = Point.fromLngLat(adapterShops.shop.getLongitude(), adapterShopsHome.shop1.getLatitude());

              }
              Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                      locationComponent.getLastKnownLocation().getLatitude());

              GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
              if (source != null) {
                  source.setGeoJson(Feature.fromGeometry(destinationPoint));
              }
              try {
                  Geocoder geocoder = new Geocoder(MapBoxActivity.this, Locale.getDefault());
                  List<Address> addresses = geocoder.getFromLocation( locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude(),1);
                  String address = addresses.get(0).getAddressLine(0);
                  textLocation.setText(address);
              } catch (IOException e) {
                  e.printStackTrace();
              }
              getRoute(originPoint, destinationPoint);
              mapboxProgressBar.setVisibility(View.VISIBLE);
              return true;
          }

          private void getRoute(Point origin, Point destination) {
              NavigationRoute.builder(this)
                      .accessToken(Mapbox.getAccessToken())
                      .origin(origin)
                      .destination(destination)
                      .build()
                      .getRoute(new Callback<DirectionsResponse>() {
                          @Override
                          public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                              Log.d(TAG, "Response code: " + response.code());
                              if (response.body() == null) {
                                  Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                                  return;
                              } else if (response.body().routes().size() < 1) {
                                  Log.e(TAG, "No routes found");
                                  return;
                              }

                              currentRoute = response.body().routes().get(0);

// Draw the route on the map
                              if (navigationMapRoute != null) {
                                  navigationMapRoute.removeRoute();
                              } else {
                                  navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                              }
                              navigationMapRoute.addRoute(currentRoute);
                              mapboxProgressBar.setVisibility(View.INVISIBLE);
                              startButton.setEnabled(true);
                          }

                          @Override
                          public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                              Log.e(TAG, "Error: " + throwable.getMessage());
                          }
                      });
          }

          @SuppressWarnings( {"MissingPermission"})
          private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
              if (PermissionsManager.areLocationPermissionsGranted(this)) {
// Activate the MapboxMap LocationComponent to show user location
// Adding in LocationComponentOptions is also an optional parameter
                  locationComponent = mapboxMap.getLocationComponent();
                  locationComponent.activateLocationComponent(this, loadedMapStyle);
                  locationComponent.setLocationComponentEnabled(true);
// Set the component's camera mode
                  locationComponent.setCameraMode(CameraMode.TRACKING);
              } else {
                  permissionsManager = new PermissionsManager(this);
                  permissionsManager.requestLocationPermissions(this);
              }
          }

          @Override
          public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
              permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
          }

          @Override
          public void onExplanationNeeded(List<String> permissionsToExplain) {
              Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
          }

          @Override
          public void onPermissionResult(boolean granted) {
              if (granted) {
                  enableLocationComponent(mapboxMap.getStyle());
              } else {
                  Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
                  finish();
              }
          }

          @Override
          protected void onStart() {
              super.onStart();
              mapView.onStart();
          }

          @Override
          protected void onResume() {
              super.onResume();
              mapView.onResume();
          }

          @Override
          protected void onPause() {
              super.onPause();
              mapView.onPause();
          }

          @Override
          protected void onStop() {
              super.onStop();
              mapView.onStop();
          }

          @Override
          protected void onSaveInstanceState(Bundle outState) {
              super.onSaveInstanceState(outState);
              mapView.onSaveInstanceState(outState);
          }

          @Override
          protected void onDestroy() {
              super.onDestroy();
              mapView.onDestroy();
          }

          @Override
          public void onLowMemory() {
              super.onLowMemory();
              mapView.onLowMemory();
          }

      }