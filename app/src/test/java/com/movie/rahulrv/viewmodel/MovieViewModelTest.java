package com.movie.rahulrv.viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movie.rahulrv.MovieAPI;
import com.movie.rahulrv.model.Movie;
import com.movie.rahulrv.model.MovieWrapper;
import com.movie.rahulrv.model.VideoWrapper;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * MovieViewModel test
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieViewModelTest {

    @Mock
    MovieAPI movieAPI;

    private Gson gson;

    @Before
    public void setup() throws Exception {

        gson = new GsonBuilder().create();
        when(movieAPI.nowPlaying(1)).thenReturn(Observable.just(nowPlaying()));
        when(movieAPI.getVideos(335791)).thenReturn(Observable.just(videos()));
    }

    @Test
    public void load_nowPlaying() throws Exception {
        MovieViewModel viewModel = new MovieViewModel(movieAPI);

        TestSubscriber<List<Movie>> testSubscriber = new TestSubscriber<>();
        viewModel.loadMovies().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);

        verify(movieAPI, times(1)).nowPlaying(anyInt());
        verify(movieAPI).nowPlaying(anyInt());
    }

    @Test
    public void isLoading_toggled() throws Exception
    {
        MovieViewModel viewModel = new MovieViewModel(movieAPI);

        // Initial state - not loading
        TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        viewModel.getIsLoading().subscribe(testSubscriber);
        testSubscriber.assertValue(false);

        // Start loading page
        Observable<List<Movie>> postsObservable = viewModel.loadMovies();

        testSubscriber = new TestSubscriber<>();
        viewModel.getIsLoading().subscribe(testSubscriber);
        testSubscriber.assertValue(true);

        // Finish loading page
        postsObservable.subscribe();

        testSubscriber = new TestSubscriber<>();
        viewModel.getIsLoading().subscribe(testSubscriber);
        testSubscriber.assertValue(false);
    }


    private MovieWrapper nowPlaying() throws Exception {
        return gson.fromJson(IOUtils.toString(getClass().getResourceAsStream("/now_playing.json")), MovieWrapper.class);
    }

    private VideoWrapper videos() throws Exception {
        return gson.fromJson(IOUtils.toString(getClass().getResourceAsStream("/videos.json")), VideoWrapper.class);
    }
}
