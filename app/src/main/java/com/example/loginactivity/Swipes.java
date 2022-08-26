package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Swipes extends AppCompatActivity {

    private static final String TAG = "Swipes";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    Button history, playlist, pickArtist;
    DatabaseReference databaseReference;

    private List<ItemModel> items = addList();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipes);

        pickArtist = findViewById(R.id.pickArtistBtn);
        playlist = findViewById(R.id.playlistBtn);
        history = findViewById(R.id.historyBtn);

        String putTogetter = "Users/"+Login.user;
        databaseReference = FirebaseDatabase.getInstance().getReference(putTogetter);

        pickArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickArtist.class);
                startActivity(intent);
            }
        });

        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Playlist.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                startActivity(intent);
            }
        });

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + "ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if(direction == Direction.Right){
                    Toast.makeText(Swipes.this, "Added to Playlist", Toast.LENGTH_SHORT).show();
                    Testing_HelperClass helperClass = new Testing_HelperClass(items.get(i).getNama(), "Like", items.get(i).getUrla());
                    databaseReference.child(items.get(i).getNama()).setValue(helperClass);
                    i++;
                }
                if(direction == Direction.Left){
                    Toast.makeText(Swipes.this, "Showing Less Like This", Toast.LENGTH_SHORT).show();
                    Testing_HelperClass helperClass = new Testing_HelperClass(items.get(i).getNama(), "Dislike", items.get(i).getUrla());
                    databaseReference.child(items.get(i).getNama()).setValue(helperClass);
                    i++;
                }

                if(manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.all_too_well, "All Too Well", "Taylor Swift", "Country", "https://open.spotify.com/track/5enxwA8aAbwZbf5qCHORXi?si=5d9a373e038c468d"));
        items.add(new ItemModel(R.drawable.come_together, "Come Together", "The Beatles", "Rock", "https://open.spotify.com/track/2EqlS6tkEnglzr7tkKAAYD?si=b37fd80b61624f6a"));
        items.add(new ItemModel(R.drawable.new_person_same_old_mistakes, "New Person Same Old Mistakes", "Tame Impala", "Alternative", "https://open.spotify.com/track/52ojopYMUzeNcudsoz7O9D?si=6ddf22e902e94fea"));
        items.add(new ItemModel(R.drawable.the_great_gig_in_the_sky, "The Great Gig in the Sky", "Pink Floyd", "Rock", "https://open.spotify.com/track/2TjdnqlpwOjhijHCwHCP2d?si=bcab4a9827344a0a"));
        items.add(new ItemModel(R.drawable.rip_fredo, "R.I.P Fredo", "Playboi Carti", "Hip Hop", "https://open.spotify.com/track/45Ln3F9PRPYTXBcMFkZMzS?si=acb0b3d9365b40e0"));
        items.add(new ItemModel(R.drawable.them_changes, "Them Changes", "Thundercat", "Funk", "https://open.spotify.com/track/7CH99b2i1TXS5P8UUyWtnM?si=3fbdd98520b14fcd"));
        items.add(new ItemModel(R.drawable.the_girl_from_ipanema, "The Girl From Ipanema", "Stan Getz, Joao Gilberto", "Bossa Nova", "https://open.spotify.com/track/4qGfJb2KByjvzrwo8HNibg?si=8eed0e0f22594138"));
        items.add(new ItemModel(R.drawable.seven_rings, "7 Rings", "Ariana Grande", "Hip Hop", "https://open.spotify.com/album/03D36t8ODrA8ATvoTStU9P"));
        items.add(new ItemModel(R.drawable.one_dance, "One Dance", "Drake", "Hip Hop", "https://open.spotify.com/album/05FOcj5Cygu51XbAmlJd2k"));
        items.add(new ItemModel(R.drawable.drip_too_hard, "Drip Too Hard", "Lil Baby", "Hip Hop", "https://open.spotify.com/album/0LfKQSbicPG4QTTVkS0fes"));
        items.add(new ItemModel(R.drawable.laugh_now_cry_later, "Laugh Now Cry Later", "Drake", "Hip Hop", "https://open.spotify.com/album/0qGdc7fNq9RNIPEzZufa43"));
        items.add(new ItemModel(R.drawable.baby, "Baby", "DaBaby", "Hip Hop", "https://open.spotify.com/album/0ssH9wMngti9j73Rb9kcfO"));
        items.add(new ItemModel(R.drawable.yes_indeed, "Yes Indeed", "Lil Baby", "Rock","https://open.spotify.com/album/0yIKS2aYytEeKtXuQvOPOj"));
        items.add(new ItemModel(R.drawable.moves_like_jagger, "Moves Like Jagger", "Maroon 5", "Pop", "https://open.spotify.com/album/1kPZdeot5lS7X0a7j4pHIi"));
        items.add(new ItemModel(R.drawable.bodak_yellow, "Bodak Yellow", "Cardi B", "Hip Hop", "https://open.spotify.com/album/1wpgafbHuQfSUmCWvahFlq"));
        items.add(new ItemModel(R.drawable.the_hills, "The Hills", "The Weeknd", "EDM", "https://open.spotify.com/album/1XO0SIspYtH1EKFs17UAtj"));
        items.add(new ItemModel(R.drawable.rockstar, "rockstar", "Post Malone", "Hip Hop", "https://open.spotify.com/album/1YCRXKLDRB0UnbrFUJ4F6e"));
        items.add(new ItemModel(R.drawable.on_me, "On Me", "Lil Baby", "Hip Hop", "https://open.spotify.com/album/25gJpQKgXxT5CrMitr0DQO"));
        items.add(new ItemModel(R.drawable.heat, "Heat", "Chris Brown", "Hip Hop", "https://open.spotify.com/album/2gbbHSH4vizMYgfl4F3nH1"));
        items.add(new ItemModel(R.drawable.ocean_eyes, "ocean eyes", "Billie Eilish", "Alternative", "https://open.spotify.com/album/2msN7XBgV3JCjQ7Tq3t7i9"));
        items.add(new ItemModel(R.drawable.wap, "WAP", "Cardi B", "Hip Hop", "https://open.spotify.com/album/2ogiazbrNEx0kQHGl5ZBTQ"));
        items.add(new ItemModel(R.drawable.lovely, "lovely", "Billie Eilish", "Alternative", "https://open.spotify.com/album/2sBB17RXTamvj7Ncps15AK"));
        items.add(new ItemModel(R.drawable.blinding_lights, "Blinding Lights", "The Weeknd", "EDM", "https://open.spotify.com/album/2ZfHkwHuoAZrlz7RMj0PDz"));
        items.add(new ItemModel(R.drawable.lucid_dreams, "Lucid Dreams", "Juice WRLD", "Hip Hop", "https://open.spotify.com/album/3b5sEYrFMS3DO6K9TlfWK6"));
        items.add(new ItemModel(R.drawable.focus, "Focus", "Ariana Grande", "Pop", "https://open.spotify.com/album/3IGM1sXYke2UGII2DORrof"));
        items.add(new ItemModel(R.drawable.payphone, "Payphone", "Maroon 5", "Pop", "https://open.spotify.com/album/3MLzYyjkFBMPTgsso73O36"));
        items.add(new ItemModel(R.drawable.memories, "Memories", "Maroon 5", "Pop", "https://open.spotify.com/album/3nR9B40hYLKLcR0Eph3Goc"));
        items.add(new ItemModel(R.drawable.or_nah, "Or Nah", "The Weeknd", "Hip Hop", "https://open.spotify.com/album/3SHx7bBQFI4J8QRr6D5cOK"));
        items.add(new ItemModel(R.drawable.bandit, "Bandit", "Juice WRLD", "Hip Hop", "https://open.spotify.com/album/3t6Z2qoBVCS4NHNI25XECH"));
        items.add(new ItemModel(R.drawable.sunflower, "Sunflower", "Post Malone", "Hip Hop", "https://open.spotify.com/album/47LpgGVshd0tbFSbm9tTLb"));
        items.add(new ItemModel(R.drawable.congratulations, "Congratulations", "Post Malone", "Hip Hop", "https://open.spotify.com/album/4BC7xFBCxUMBEgGpxRBaCy"));
        items.add(new ItemModel(R.drawable.goodbyes, "Goodbyes", "Post Malone", "Hip Hop", "https://open.spotify.com/album/4FFBjcmX06VmazABtpRMyv"));
        items.add(new ItemModel(R.drawable.everything_i_wanted, "everything i wanted", "Billie Eilish", "Alternative", "https://open.spotify.com/album/4i3rAwPw7Ln2YrKDusaWyT"));
        items.add(new ItemModel(R.drawable.stay, "STAY", "Justin Bieber", "Pop", "https://open.spotify.com/album/4QLAtpLNUsHEYrcHXmMIZZ"));
        items.add(new ItemModel(R.drawable.love_yourself, "Love Yourself", "Justin Bieber", "Pop", "https://open.spotify.com/album/4qlyglewxzSY1mmwtGF4BM"));
        items.add(new ItemModel(R.drawable.starboy, "Starboy", "The Weeknd", "EDM", "https://open.spotify.com/album/4rnl39iMQXOYZcw9J7ml4y"));
        items.add(new ItemModel(R.drawable.intentions, "intentions", "Justin Bieber", "Pop", "https://open.spotify.com/album/55zg331p7m1EFA4uRggkwt"));
        items.add(new ItemModel(R.drawable.please_me, "Please Me", "Cardi B", "Hip Hop", "https://open.spotify.com/album/5a4sJJ3qjn6hqRsvm0Veso"));
        items.add(new ItemModel(R.drawable.up, "Up", "Cardi B", "Hip Hop", "https://open.spotify.com/album/5BNrcvfbLyADks4RXPW7VP"));
        items.add(new ItemModel(R.drawable.back_to_sleep, "Back to Sleep", "Chris Brown", "Hip Hop", "https://open.spotify.com/album/5GIDk7gHi6x19CxZeackUx"));
        items.add(new ItemModel(R.drawable.i_dont_care, "I Don't Care", "Justin Bieber", "Pop", "https://open.spotify.com/album/5Nux7ozBJ5KJ02QYWwrneR"));
        items.add(new ItemModel(R.drawable.robbery, "Robbery", "Juice WRLD", "Hip Hop", "https://open.spotify.com/album/5PGOpr83n3i3HGJeyhan3j"));
        items.add(new ItemModel(R.drawable.adore_you, "Adore You", "Harry Styles", "Pop", "https://open.spotify.com/album/5SL9nXZYZZl68bHwaM8uLa"));
        items.add(new ItemModel(R.drawable.adore_you, "Watermelon Sugar", "Harry Styles", "Pop", "https://open.spotify.com/album/659e2eKbsMH0vYCs5qgFmy"));
        items.add(new ItemModel(R.drawable.bodak_yellow, "I Like It", "Cardi B", "Hip Hop", "https://open.spotify.com/album/6oRrfGcUeAwfX1lTdxZFFj"));
        items.add(new ItemModel(R.drawable.heat, "No Guidance", "Chris Brown", "Hip Hop", "https://open.spotify.com/album/6VJoSzF68WoXVRwtKEcpNW"));
        items.add(new ItemModel(R.drawable.sign_of_the_times, "Sign of the Times", "Harry Styles", "Pop", "https://open.spotify.com/album/6YDkzHVTEzMXZOVd1r5NqR"));
        items.add(new ItemModel(R.drawable.into_you, "Into You", "Ariana Grande", "Hip Hop", "https://open.spotify.com/album/7uOnBLb0pMf3ASnHrrNSUY"));
        items.add(new ItemModel(R.drawable.back_in_black, "Back in Black", "AC/DC", "Rock", "https://open.spotify.com/track/08mG3Y1vljYA6bvDt4Wqkj?si=c6bd548c1e0d40ac"));
        items.add(new ItemModel(R.drawable.call_out_my_name, "Call out my Name", "The Weeknd", "Pop", "https://open.spotify.com/track/09mEdoA6zrmBPgTEN5qXmN"));
        items.add(new ItemModel(R.drawable.clap, "Clap", "Seventeen", "EDM", "https://open.spotify.com/track/19t5GSN3XsLB7UOsZD8Fwv"));
        items.add(new ItemModel(R.drawable.all_too_well, "Lover", "Taylor Swift", "Rock", "https://open.spotify.com/track/1dGr1c8CrMLDpV6mPbImSI"));
        items.add(new ItemModel(R.drawable.go_crazy, "Go Crazy", "Chris Brown", "Hip Hop", "https://open.spotify.com/track/1IIKrJVP1C9N7iPtG6eOsK"));
        items.add(new ItemModel(R.drawable.suicidial, "Suicidal", "Juice WRLD", "Hip Hop", "https://open.spotify.com/track/1iSqfoUFnQwV0QW1EfUit8"));
        items.add(new ItemModel(R.drawable.blank_space, "Blank Space", "Taylor Swift", "Rock", "https://open.spotify.com/track/1p80LdxRV74UKvL8gnD7ky"));
        items.add(new ItemModel(R.drawable.very_nice, "VERY NICE", "Seventeen", "EDM", "https://open.spotify.com/track/1Rrj7KyS2R6SP9CQMDJW1w"));
        Collections.shuffle(items);
        return items;
    }
}