CREATE FUNCTION total_score(stats)
  RETURNS numeric(1,1) AS
$BODY$
BEGIN
  CASE (SELECT stats.position FROM stats WHERE stats.player_id = $1.player_id)
    WHEN 'RB' THEN
      RETURN ROUND((SELECT sum(20*(rushyards/(SELECT max(rushyards) FROM stats WHERE games_played > 10)) + 7.5*(rushattempts/(SELECT max(rushattempts) FROM stats WHERE games_played > 10)) + 7.5*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) + 5*(recaverage/(SELECT max(recaverage) FROM stats WHERE games_played > 10)) + 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 10*(rushfumbles/(SELECT max(rushfumbles) FROM stats WHERE games_played > 10)) - 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10))))/54.270042194092827004200, 2) * 100
      FROM stats
      WHERE stats.player_id = $1.player_id;
    WHEN 'QB' THEN
      RETURN ROUND((SELECT sum(10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10)) + 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10)) + 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) + 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10)) + 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10)) - 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10)) - 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) - 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10)) - 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10))))/44.737630759217865, 2) * 100
      FROM stats
      WHERE stats.player_id = $1.player_id;
    WHEN 'TE' THEN
      RETURN ROUND((SELECT sum(15*(receptions/(SELECT max(receptions) FROM stats WHERE games_played > 10)) + 12.5*(recyards/(SELECT max(recyards) FROM stats WHERE games_played > 10)) + 12.5*(recyardspergame/(SELECT max(recyardspergame) FROM stats WHERE games_played > 10)) + 10*(targets/(SELECT max(targets) FROM stats WHERE games_played > 10)) + 20*(rectd/(SELECT max(rectd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10))))/55.282834761586810688325, 2) * 100
      FROM stats
      WHERE stats.player_id = $1.player_id;
    WHEN 'K' THEN
      RETURN ROUND((SELECT sum(12.5*(xpmade/(SELECT max(xpmade) FROM stats WHERE games_played > 10)) + 10*(xppct/(SELECT max(xppct) FROM stats WHERE games_played > 10)) + 20*(fgmade/(SELECT max(fgmade) FROM stats WHERE games_played > 10)) + 10*(fgpct/(SELECT max(fgpct) FROM stats WHERE games_played > 10)) + 5*(fg40_49pct/(SELECT max(fg40_49pct) FROM stats WHERE games_played > 10)) + 5*(fglng/(SELECT max(fglng) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 10*(fgblk/(SELECT max(fgblk) FROM stats WHERE games_played > 10))))/69.752158113551268317525, 2) * 100
      FROM stats
      WHERE stats.player_id = $1.player_id;
    WHEN 'WR' THEN
      RETURN ROUND((SELECT sum(15*(receptions/(SELECT max(receptions) FROM stats WHERE games_played > 10)) + 12.5*(recyards/(SELECT max(recyards) FROM stats WHERE games_played > 10)) + 12.5*(recyardspergame/(SELECT max(recyardspergame) FROM stats WHERE games_played > 10)) + 10*(targets/(SELECT max(targets) FROM stats WHERE games_played > 10)) + 20*(rectd/(SELECT max(rectd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10))))/73.424258409484238627275, 2) * 100
      FROM stats
      WHERE stats.player_id = $1.player_id;
    ELSE RETURN null;
  END CASE;
END
$BODY$
LANGUAGE 'plpgsql' IMMUTABLE;
