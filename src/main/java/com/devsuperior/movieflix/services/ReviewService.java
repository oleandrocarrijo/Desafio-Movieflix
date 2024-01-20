package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {

        Movie movie = movieRepository.findById(dto.getMovieId()).get();
        UserDTO profile = userService.getProfile();
        User user = userRepository.findById(profile.getId()).get();

        Review entity = new Review();
        entity.setText(dto.getText());
        entity.setMovie(movie);
        entity.setUser(user);

        return new ReviewDTO(reviewRepository.save(entity));
    }


}
