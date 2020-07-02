package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fst")
public class FollowStateTrackerController {

    @Autowired
    private FollowStateTrackerService fstService;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Global methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<FollowStateTracker>> getAllFST() {
        List<FollowStateTracker> fstList = fstService.getAllFST()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<List<FollowStateTracker>>( fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/{Id}")
    public ResponseEntity<FollowStateTracker> getFSTById(@PathVariable(value = "Id") Long Id) {
        FollowStateTracker fst = fstService.getFSTById(Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance trouvée avec l'id: " + Id));
        return new ResponseEntity<FollowStateTracker>( fst, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/follower/{followerId}")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollowerID(@PathVariable(value = "followerId") Long followerId) {
        List<FollowStateTracker> fstList = fstService.getFSTByFollowerID(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<List<FollowStateTracker>>( fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed/{followerId}")
    public ResponseEntity<List<User>> getAllFollowedByFollower(
            @PathVariable(value = "followerId") Long followerId) {
        List<User> followedList = fstService.getAllFollowedByFollowerID(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<List<User>>( followedList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follow/{followerId}/{followableId}")
    public ResponseEntity<Boolean> follow(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "followableId") Long followableId) {
        if ( fstService.follow(followerId, followableId)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>( false, HttpStatus.PRECONDITION_FAILED);
        }
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/unfollow/{fstId}")
    public ResponseEntity<Boolean> unfollowByFSTId(
            @PathVariable(value = "fstId") Long fstId) {
        fstService.unfollowByFSTId(fstId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    };

    //public void setFollowerStatusByFollowable(User follower, User followable, Follower.FollowerStatus status);
    //public void setFollowerNotifiableStatusByFollowable(User follower, User followable, Follower.FollowerNotification notifiable);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/followed/{followableId}")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollowableID(
            @PathVariable(value = "followableId") Long followableId) {
        List<FollowStateTracker> fstList = fstService.getAllFSTByFollowableID(followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<List<FollowStateTracker>>( fstList, HttpStatus.OK);
    }

    //public Optional<List<User>>  getAllFollowersBbyFollowable(User followable);
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/{followableId}")
    public ResponseEntity<List<User>> getAllFollowersBbyFollowable(
            @PathVariable(value = "followableId") Long followableId) {
        List<User> followedList = fstService.getAllFollowersByFollowableID(followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<List<User>>( followedList, HttpStatus.OK);
    };

    //public void banFollower(User followed, User follower);
    //public void setFollowableStatus(User followable, Followable.FollowableStatus status);
    //public void setFollowableNotifiableStatus(User followable, Followable.FollowableNotification notifiable);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification")
    public ResponseEntity<List<Notification>> getAllNotifications()  {
        List<Notification> notificationList = fstService.getAllNotifications()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}")
    public ResponseEntity<List<Notification>> getAllNotificationsByFollower(
            @PathVariable(value = "followerId") Long followerId)  {
        List<Notification> notificationList = fstService.getAllNotificationsByFollower(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}/{followableId}")
    public ResponseEntity<List<Notification>> getAllNotificationsByFollowerAndByFollowable(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "followableId") Long followableId)  {

        List<Notification> notificationList = fstService.getAllNotificationsByFollowerAndByFollowable(followerId, followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    //Optional<List<Notification>> getAllNotificationsByFollowable(User followable) {};

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/read/{followerId}")
    public void setNotifcationsReadStatus(
            @PathVariable(value = "followerId") Long followerId,
            @Valid @RequestBody List<Notification> notifications,
            @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {
        fstService.setNotifcationsReadStatus(followerId, notifications, isRead);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/pop/{followerId}")
    public void popNotifications(
            @PathVariable(value = "followerId") Long followerId,
            @Valid @RequestBody List<Notification> notifications) {
        fstService.popNotifications(followerId, notifications);
    };
}
