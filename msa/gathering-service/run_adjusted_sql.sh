#!/bin/bash
mysql -u msa -p123 --default-character-set=utf8mb4 msa_gathering < adjusted_inserts.sql
