package fr.uca.cdr.skillful_network.repositories.application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.application.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long>{
	List<Comment> findAllByCommentId(Long id);
	List<Comment> findAllByPostId(Long id);
}
