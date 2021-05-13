UPDATE "students" SET "deleted" = 'f' WHERE "deleted" IS NULL;

ALTER TABLE "students" ALTER COLUMN "deleted" SET NOT NULL;

ALTER TABLE "students" ALTER COLUMN "deleted" SET DEFAULT 'f';
