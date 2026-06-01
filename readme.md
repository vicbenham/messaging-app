# Solution

## Story 1
    Implique une création d'users ??

    Une table avec des users et une table avec des jonctions entre chaque users

    Une table avec les discussions avec relations many to many

    User => name, telephone

    discussion => user1, user2, status (pending, accepted, started, declined), updated_At new_content

    Messages => msg(500charac max) ou url du file, sended_at, discussion_id, send_by, is_updated

## Story 2
    Ajout de la colonne pending dans la table jonctions et passe à pending si pas de soucis (existing, declined, not found)

## Story 3
    Voir les relations en pending pour un user

## Story 4
    SSE et si new_content = true et sended_at > 24h => send mail

    Si lu => new_content = false

## Story 5
    SSE et si pending => Accepted et updated_at > 24h => send mail

    Si lu => pending = validated

## Story 6
    Si validated => création d'une discussion

    envoie de message possible

## Story 8
    Multi part file => format classique, taille max 20Mo
    
    save on server

## Story 9
    Remove on server file => replace by "file deleted"

## Story 10
    Edit message by message_id et is_updated => true

## Story 11
    Status = declined

## Story 12
    Discussion deleted

## Story 13
    replace msg by msg deleted

## Story 14
    notify on connait toi même tu sais

## Story 15
    logs de chaque activité => logger dans les services

## Story 16
    notify on connait toi même tu sais