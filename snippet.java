


public class RestManager {
    private Retrofit retrofit;
    private OdessaService service;

    public RestManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mydomain.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OdessaService.class);
    }

    public OdessaService getOdessaService() {
        return service;
    }
	
	public interface OdessaService {
	    @GET("index.php?task=fck") // index.php?task=fck&catID=1
	    Call<List<Category>> getAllCategoryItems(@Query("catID") int catid);
	}

}


//class activity

RestManager rManager = new RestManager();
Call<List<Category>> listCall = rManager.getOdessaService().getAllCategoryItems(...); //int catid


listCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {

                    ListView listViewTitle = (ListView) findViewById(R.id.listViewTitle);
                    ArrayList<String> title = new ArrayList<>();

                    List<Category> items = response.body();

                    for (int i = 0; i < items.size(); i++) {
                        Category cat = items.get(i);
                        title.add(cat.getTitle());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, title);
                    listViewTitle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    Log.d("xxxxxxqwevmxxxxxxx", title.toString());

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

