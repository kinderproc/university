UPDATE "groups" SET "deleted" = 'f' WHERE "deleted" IS NULL;

ALTER TABLE "groups" ALTER COLUMN "deleted" SET NOT NULL;

ALTER TABLE "groups" ALTER COLUMN "deleted" SET DEFAULT 'f';
