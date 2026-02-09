DROP INDEX STG_AREA_IDENTI_UK;

CREATE UNIQUE INDEX STG_AREA_IDENTI_UK ON STG_AREA (
                                                    ARE_IDENTI ASC,
                                                    ARE_CODENT ASC
);
