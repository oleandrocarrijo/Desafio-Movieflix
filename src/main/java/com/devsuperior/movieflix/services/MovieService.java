package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieCardProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private GenreRepository genreRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Movie movie = movieRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new MovieDetailsDTO(movie);
	}


	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {

		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title").ascending());

		if (genreId != null) {
			Page<MovieCardProjection> page = movieRepository.findByGenreId(genreId, pageable);
			return page.map(MovieCardDTO::new);
		}

		Page<Movie> page = movieRepository.findAll(pageable);
		return page.map(MovieCardDTO::new);
    }
}