CREATE FUNCTION total_score(team_stats)
  RETURNS numeric AS
$BODY$
BEGIN
  RETURN ROUND((SELECT sum(10*(tackletotal/ (SELECT max(tackletotal) FROM team_stats) + 10*(sacks/(SELECT max(sacks) FROM team_stats )) + 10*(passesdefended/(SELECT max(passesdefended) FROM team_stats  )) + 10*(interceptions/(SELECT max(interceptions) FROM team_stats  )) + 10*(intyds/(SELECT max(intyds) FROM team_stats  )) + 10*(inttd)/(SELECT max(inttd) FROM team_stats  )) + 10*(fumforced/(SELECT max(fumforced) FROM team_stats  )) + 10*(fumopprec/(SELECT max(fumopprec) FROM team_stats  )) + 10*(fumtd/(SELECT max(fumtd) FROM team_stats  ))))/508.10989168319169685840, 2) * 100
  FROM team_stats
  WHERE team_stats.teamid = $1.teamid;
END
$BODY$
LANGUAGE 'plpgsql';
