package com.sparta.doom.fantasticninewebandapi.models.theater.repositories;

import com.sparta.doom.fantasticninewebandapi.models.CommentDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends MongoRepository<CommentDoc, ObjectId> {
}
