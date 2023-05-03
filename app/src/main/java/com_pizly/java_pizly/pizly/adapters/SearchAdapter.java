package com_pizly.java_pizly.pizly.adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com_pizly.java_pizly.pizly.models.ItemSearcher.LayoutOne;
import static com_pizly.java_pizly.pizly.models.ItemSearcher.LayoutThree;
import static com_pizly.java_pizly.pizly.models.ItemSearcher.LayoutTwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.models.ItemSearcher;
import com_pizly.java_pizly.pizly.models.Party;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.ui.activeTckets.TicketDataActivity;
import com_pizly.java_pizly.pizly.ui.friends.FriendAdditionFragment;
import com_pizly.java_pizly.pizly.ui.friends.FriendDataActivity;
import com_pizly.java_pizly.pizly.ui.yourParties.UpdateYourPartyActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter extends RecyclerView.Adapter {

    private List<ItemSearcher> searcherList;
    private Context context;
    private FragmentManager fragmentManager;
    private Activity activity;

    public SearchAdapter(List<ItemSearcher> searcherList, Context context, FragmentManager fragmentManager, Activity activity) {
        this.searcherList = searcherList;
        this.context = context;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    public void filterList(List<ItemSearcher> filterlist) {
        searcherList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position) {

        int uid = SharedPrefManager.getInstance(context).getKeyID();

        switch (searcherList.get(position).getViewType()) {
            case LayoutOne:
                String text = searcherList.get(position).getTitle();
                String image = searcherList.get(position).getImageUser();
                int id = searcherList.get(position).getPersonID();
                ((SearchPeopleViewHolder) holder).setViews(image, text);



                ((SearchPeopleViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Person person = new Person();
                        person.setName(text);
                        person.setImage(image);
                        person.setId(id);

                        Call<List<BannedUser>> usersCall = ApiClient.getApiService().getBlockedFriends(uid);
                        usersCall.enqueue(new Callback<List<BannedUser>>() {
                            @Override
                            public void onResponse(Call<List<BannedUser>> call, Response<List<BannedUser>> response) {
                                for (BannedUser user : response.body()) {
                                    if (user.getBlockToID() == id) {
                                        context.startActivity(new Intent(context, FriendDataActivity.class)
                                                .setFlags(FLAG_ACTIVITY_NEW_TASK)
                                                .putExtra("ID", id)
                                                .putExtra("blockToID", user.getBlockToID())
                                                .putExtra("rowBlockedID", user.getId()));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BannedUser>> call, Throwable t) {

                            }
                        });

                        getFriends(id, uid);


                        FriendAdditionFragment friendAdditionFragment = new FriendAdditionFragment();
                        friendAdditionFragment.setPerson(person);
                        friendAdditionFragment.show(fragmentManager, friendAdditionFragment.getTag());


                    }
                });
                break;

            case LayoutTwo:
                String imageParty = searcherList.get(position).getImageParty();
                String titleParty = searcherList.get(position).getTitle();
                ((SearchYourPartiesViewHolder) holder).setViews(imageParty, titleParty);

                ((SearchYourPartiesViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Call<List<Party>> myPartiesCall = ApiClient.getApiService().getParties(uid);
                        myPartiesCall.enqueue(new Callback<List<Party>>() {
                            @Override
                            public void onResponse(Call<List<Party>> call, Response<List<Party>> response) {
                                for(Party party : response.body()){
                                    Intent intent = new Intent(context, UpdateYourPartyActivity.class);

                                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK)
                                            .putExtra("title", party.getTitle())
                                            .putExtra("rules", party.getRules())
                                            .putExtra("additional_info", party.getAdditional_info())
                                            .putExtra("imagePath", party.getImageParty())
                                            .putExtra("ID", party.getId());

                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Party>> call, Throwable t) {

                            }
                        });


                    }
                });


                break;

            case LayoutThree:
                int imageTicket = searcherList.get(position).getImageTicket();
                String titleTicket = searcherList.get(position).getTitle();
                ((SearchTicketsViewHolder) holder).setViews(imageTicket, titleTicket);
                ((SearchTicketsViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TicketDataActivity.class);
                        intent.putExtra("enabling", true).setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                return;
        }
    }

    private void getFriends(int id, int uid) {
        Call<List<Friend>> friendsCall = ApiClient.getApiService().getFriends(id, id);
        friendsCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                for (Friend friend : response.body()) {
                    if ((friend.getFriendID() == id && friend.getUid() == uid) ||
                            (friend.getUid() == id) && friend.getFriendID() == uid) {
                        context.startActivity(new Intent(context, FriendDataActivity.class).setFlags(FLAG_ACTIVITY_NEW_TASK).putExtra("ID", id));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LayoutOne:
                View layoutOne
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_person, parent,
                                false);
                return new SearchAdapter.SearchPeopleViewHolder(layoutOne);
            case LayoutTwo:
                View layoutTwo
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_your_party_searcher, parent,
                                false);
                return new SearchAdapter.SearchYourPartiesViewHolder(layoutTwo);
            case LayoutThree:
                View layoutThree
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_ticket_searcher, parent,
                                false);
                return new SearchAdapter.SearchTicketsViewHolder(layoutThree);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (searcherList.get(position).getViewType()) {
            case 0:
                return LayoutOne;
            case 1:
                return LayoutTwo;
            case 2:
                return LayoutThree;
            default:
                return -1;
        }
    }


    @Override
    public int getItemCount() {
        return searcherList.size();
    }

    public class SearchYourPartiesViewHolder extends RecyclerView.ViewHolder {
        private TextView titleParty;
        private ImageView partyImage;

        public SearchYourPartiesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleParty = itemView.findViewById(R.id.text_view_title_your_party_searcher);
            partyImage = itemView.findViewById(R.id.image_view_party);

        }

        private void setViews(String imagePartyURI, String partyTitle) {
            Picasso.get().load(imagePartyURI).into(partyImage);
            titleParty.setText(partyTitle);
        }
    }

    public class SearchTicketsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTicket;
        private ImageView ticketImage;

        public SearchTicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTicket = itemView.findViewById(R.id.text_view_title_ticket_searcher);
            ticketImage = itemView.findViewById(R.id.image_view_ticket);

        }

        private void setViews(int ticketImageI, String title) {
            ticketImage.setImageResource(ticketImageI);
            titleTicket.setText(title);
        }
    }

    public class SearchPeopleViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;

        public SearchPeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_name);
            image = itemView.findViewById(R.id.image_view_person);

        }

        private void setViews(String imageProfile, String title) {
            Picasso.get().load(imageProfile).into(image);
            name.setText(title);
        }
    }
}
