(define (domain MST)
(:requirements :typing)
(:types node claw)
  
(:predicates 	(at ?x - node )
				(link ?from ?to - node)
				(opened ?c - claw)
				(closed ?c - claw)
				(free ?c - claw)
				(paletin ?c - claw)
				(paletat ?loc - node)
)
(:action move
		:parameters ( ?from ?to - node )
		:precondition (and (at ?from) 
				(link ?from ?to ))
		:effect (and (at ?to) (not (at ?from)))
)
(:action ouvrir_pince
	:parameters ( ?c - claw)
	:precondition (closed ?c)
	:effect (and (opened ?c) (not (closed ?c)))
)
(:action fermee_pince
	:parameters ( ?c - claw)
	:precondition (opened ?c)
	:effect (and (closed ?c) (not (opened ?c)))
)
(:action saisir_palet
		:parameters ( ?c - claw ?loc - node)
		:precondition (and (free ?c) 
					(opened ?c)
					(at ?loc)
					(paletat ?loc)
		)
		:effect (and 	(not (free ?c))
						(not (opened ?c))
						(not (paletat ?loc))
						(closed ?c)
						(paletin ?c)
		)
)
(:action deposer_palet
		:parameters ( ?c - claw ?loc - node)
		:precondition (and 
					(paletin ?c) 
					(closed ?c)
					(at ?loc)
		)
		:effect (and 	(free ?c)
						(opened ?c)
						(not(closed ?c))
						(not(paletin ?c))
						(paletat ?loc)
		)
)
		
) 
