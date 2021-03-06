SELECT
  p."nID"               AS id,
  p."nID_PlaceType"     AS type_id,
  p."sID_UA"            AS ua_id,
  p."sName"             AS name,
  p."sNameOriginal"     AS original_name,
  t."nID_Place_Parent"  AS parent_id,
  0                     AS level
FROM
  "PlaceTree" t,
  "Place" p
WHERE
  p."sID_UA" = :ua_id
  and t."nID_Place" = p."nID"