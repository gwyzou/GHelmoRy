package com.example.ghelmory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.ghelmory.database.GameAndGameInstanceDatabase;
import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;
import com.example.ghelmory.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class StatsFragmentTest {

    private static final int PLAYTIME = 60;
    private final List<Game> gameList = new ArrayList<>();
    private final List<GameInstance> gameInstanceList= new ArrayList<>();


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);



    @Before
    public void setUp(){
        GameAndGameInstanceDatabase.initDatabase(ApplicationProvider.getApplicationContext());
        GameAndGameInstanceDatabase.getInstance().clearAllTables();
        initData();
        onView(withId(R.id.navigation_stats)).perform(ViewActions.click());
    }
    @After
    public void tearDown(){
        GameAndGameInstanceDatabase.getInstance().clearAllTables();
    }





    private void initData() {
        for(int i = 0; i < 5; i++){
            Game game = new Game("Test Game"+i, "This is a test game.", 2, 4, PLAYTIME, "https://example.com/image.jpg");
            gameList.add(game);
            GameRepository.getInstance().insertGame(game);
        }
        addGameInstance(1,0);
        addGameInstance(2,0);
        addGameInstance(3,1);
        addGameInstance(4,2);
        addGameInstance(5,3);
    }
    private void addGameInstance(int number, int gameNumber){
        GameInstance gameInstance = new GameInstance();
        gameInstance.setGameId(gameList.get(gameNumber).getId());
        gameInstance.setTitle("Game Instance "+number);
        gameInstance.setDescription("This is a test game instance.");
        gameInstance.setPlaytime(PLAYTIME);
        gameInstance.setLocation("Test Location");
        gameInstance.setPhoto("https://example.com/image.jpg");
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < number; i++){
            players.add(new Player("Test Player " + i, 2));
        }
        gameInstance.setPlayers(players);
        gameInstanceList.add(gameInstance);
        GameRepository.getInstance().insertGameInstance(gameInstance);
    }

    @Test
    public void verifyIsStatsFragment() {
        onView(withId(R.id.game_spinner_container)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.stats_display_container)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_total_games_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_total_games_display)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_average_score_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_average_score_display)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_average_time_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_average_time_next_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.text_stats_average_time_display)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_stats)).check(matches(isDisplayed()));
    }

    //TEST VALUES
    @Test
    public void verifyStatsValues() {
        onView(withId(R.id.text_stats_total_games_display)).check(matches(withText("5")));

        onView(withId(R.id.text_stats_average_score_display)).check(matches(withText("2.00")));

        float n = gameInstanceList.size();
        float totalTime =PLAYTIME*n;
        float totalPlayers = n*(n+1)/2;
        String averageTime = String.format("%.2f", totalTime/totalPlayers)+" min";
        onView(withId(R.id.text_stats_average_time_display)).check(matches(withText(averageTime)));
    }

    //TEST SPINNER
    @Test
    public void onSpinnerSelect(){
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onView(withText("Test Game0")).perform(ViewActions.click());
        onView(withId(R.id.text_stats_total_games_display)).check(matches(withText("2")));
        onView(withId(R.id.text_stats_average_score_display)).check(matches(withText("2.00")));
        onView(withId(R.id.text_stats_average_time_display)).check(matches(withText("40.00 min")));

        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onView(withText("Test Game1")).perform(ViewActions.click());
        onView(withId(R.id.text_stats_total_games_display)).check(matches(withText("1")));
        onView(withId(R.id.text_stats_average_score_display)).check(matches(withText("2.00")));
        onView(withId(R.id.text_stats_average_time_display)).check(matches(withText("20.00 min")));
    }
    //TEST RECYCLER VIEW
    @Test
    public void verifyRecyclerViewWithNoSelection(){
        onView(withId(R.id.recycler_view_stats)).check(new RecyclerViewItemCountAssertion(15));
    }
    @Test
    public void verifyRecyclerViewWithSelection1(){
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onView(withText("Test Game0")).perform(ViewActions.click());
        onView(withId(R.id.recycler_view_stats)).check(new RecyclerViewItemCountAssertion(3));
    }
    @Test
    public void verifyRecyclerViewWithSelection2(){
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onView(withText("Test Game1")).perform(ViewActions.click());
        onView(withId(R.id.recycler_view_stats)).check(new RecyclerViewItemCountAssertion(3));
    }
    @Test
    public void verifyRecyclerViewWithSelection3(){
        onView(withId(R.id.spinner)).perform(ViewActions.click());
        onView(withText("Test Game2")).perform(ViewActions.click());
        onView(withId(R.id.recycler_view_stats)).check(new RecyclerViewItemCountAssertion(4));
    }

}
