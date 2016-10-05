CREATE FUNCTION total_score(stats)
  RETURNS int8 AS
$func$
  SELECT sum(10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10)) + 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10)) + 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) + 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10)) + 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10)) - 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10)) - 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) - 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10)) - 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10)))::int8
  FROM stats
  WHERE position = 'QB'
    AND stats.player_id = $1.player_id
$func$ LANGUAGE SQL STABLE;
