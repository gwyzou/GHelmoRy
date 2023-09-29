package com.example.ghelmory.viewModelsTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.gameInstanceStats.StatsViewModel;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StatsViewModelTest {

    private static final int PLAYTIME = 60;
    private final List<Game> gameList = new ArrayList<>();
    private final List<GameInstance> gameInstanceList= new ArrayList<>();
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private GameRepository mockRepository;
    public StatsViewModelTest() {
        MockitoAnnotations.openMocks(this);
        when(mockRepository.getGameInstances()).thenReturn(new MutableLiveData<>(gameInstanceList));

    }


    @Before
    public void setup() {
        initData();
    }



    private void setUpFilter(UUID gameId){
        when(mockRepository.filterDisplayByGameId(gameId))
                .thenReturn(new MutableLiveData<>(gameInstanceList.stream()
                        .filter(gameInstance -> gameInstance.getGameId().equals(gameId))
                        .collect(Collectors.toList())));
    }


    private void initData() {
        gameList.clear();
        gameInstanceList.clear();
        for(int i = 0; i < 5; i++){
            Game game = new Game("Test Game"+i, "This is a test game.", 2, 4, PLAYTIME, "https://example.com/image.jpg");
            gameList.add(game);

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
    }



    @Test
    public void testFilterDisplayByGameIdWithNullUUID(){
        try(MockedStatic<GameRepository> mockedStatic = Mockito.mockStatic(GameRepository.class)){
            mockedStatic.when(GameRepository::getInstance).thenReturn(mockRepository);
            StatsViewModel viewModel = new StatsViewModel();
            LiveData<List<GameInstance>> gameInstanceLiveData = viewModel.filterDisplayByGameId(null);
            gameInstanceLiveData.observeForever(
                    gameInstanceList -> assertEquals( this.gameInstanceList.size(),gameInstanceList.size())
            );
            verify(mockRepository).getGameInstances();
        }

    }
    @Test
    public void testFilterDisplayByGameIdWithUnSavedUUID() {
        try (MockedStatic<GameRepository> mockedStatic = Mockito.mockStatic(GameRepository.class)) {
            mockedStatic.when(GameRepository::getInstance).thenReturn(mockRepository);
            StatsViewModel viewModel = new StatsViewModel();
            UUID gameId= getUnsavedUUID();
            setUpFilter(gameId);
            LiveData<List<GameInstance>> gameInstanceLiveData = viewModel.filterDisplayByGameId(gameId);
            verify(mockRepository).filterDisplayByGameId(gameId);
            gameInstanceLiveData.observeForever(
                    gameInstanceList -> assertEquals(gameInstanceList.size(), 0)
            );
        }

    }
    private UUID getUnsavedUUID(){
        UUID gameId= UUID.randomUUID();
        long count = -1;
        while (count!=0){
            gameId = UUID.randomUUID();
            UUID finalGameId = gameId;
            count = gameList.stream().filter(game -> game.getId().equals(finalGameId)).count();
        }
        return gameId;
    }

    @Test
    public void testFilterDisplayByGameIdWithSavedUUID() {


        try(MockedStatic<GameRepository> mockedStatic = Mockito.mockStatic(GameRepository.class)){
            mockedStatic.when(GameRepository::getInstance).thenReturn(mockRepository);
            StatsViewModel viewModel = new StatsViewModel();

            UUID gameId = gameList.get(0).getId();
            setUpFilter(gameId);

            LiveData<List<GameInstance>> gameInstanceLiveData = viewModel.filterDisplayByGameId(gameId);
            gameInstanceLiveData.observeForever(
                    gameInstanceList -> assertEquals(gameInstanceList.size(), 2)
            );


            verify(mockRepository).filterDisplayByGameId(gameId);
        }
    }

    @Test
    public void setValuesGame0(){
        StatsViewModel viewModel = new StatsViewModel();
        List<GameInstance> gameInstances = gameInstanceList.stream().filter(
                gameInstance -> gameInstance.getGameId().equals(gameList.get(0).getId())).collect(Collectors.toList()
        );
        viewModel.setValues(gameInstances);
        assertEquals(viewModel.getAVGScore().getValue(), String.format("%.2f", 2.0));
        assertEquals(viewModel.getTotal().getValue(), String.valueOf(gameInstances.size()));
        int size = 0;
        for(GameInstance gameInstance: gameInstances){
            size += gameInstance.getPlayers().size();
        }
        int finalSize = size;
        viewModel.getPlayers().observeForever(
                players -> assertEquals(players.size(), finalSize)
        );
        float AVGTime = ((float)PLAYTIME*gameInstances.size())/size;
        assertEquals(viewModel.getAVGTime().getValue(), String.format("%.2f", AVGTime)+" min");


    }
    @Test
    public void setValuesGame1(){
        StatsViewModel viewModel = new StatsViewModel();
        List<GameInstance> gameInstances = gameInstanceList.stream().filter(
                gameInstance -> gameInstance.getGameId().equals(gameList.get(1).getId())).collect(Collectors.toList()
        );
        viewModel.setValues(gameInstances);
        assertEquals(viewModel.getAVGScore().getValue(), String.format("%.2f", 2.0));
        assertEquals(viewModel.getTotal().getValue(), String.valueOf(gameInstances.size()));
        int size = 0;
        for(GameInstance gameInstance: gameInstances){
            size += gameInstance.getPlayers().size();
        }
        int finalSize = size;
        viewModel.getPlayers().observeForever(
                players -> assertEquals(players.size(), finalSize)
        );
        float AVGTime = ((float)PLAYTIME*gameInstances.size())/size;
        assertEquals(viewModel.getAVGTime().getValue(), String.format("%.2f", AVGTime)+" min");


    }
    @Test
    public void setValuesGameAll(){
        StatsViewModel viewModel = new StatsViewModel();
        List<GameInstance> gameInstances = gameInstanceList;
        viewModel.setValues(gameInstances);
        assertEquals(viewModel.getAVGScore().getValue(), String.format("%.2f", 2.0));
        assertEquals(viewModel.getTotal().getValue(), String.valueOf(gameInstances.size()));
        int size = 0;
        for(GameInstance gameInstance: gameInstances){
            size += gameInstance.getPlayers().size();
        }
        int finalSize = size;
        viewModel.getPlayers().observeForever(
                players -> assertEquals(players.size(), finalSize)
        );
        float AVGTime = ((float)PLAYTIME*gameInstances.size())/size;
        assertEquals(viewModel.getAVGTime().getValue(), String.format("%.2f", AVGTime)+" min");


    }


}
