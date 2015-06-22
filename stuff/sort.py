#!/usr/bin/env python
#-*- coding:utf-8 -*
import os,sys,csv
csv.field_size_limit(sys.maxsize)

a = 'hello'
print a
 
reader = csv.DictReader(open('20150608ce_space.tsv','r'), fieldnames = ['A', 'B', 'C'], delimiter="\t", quoting=csv.QUOTE_NONE)
reader = sorted(reader, key=lambda some: some['A']);
writer = csv.DictWriter(open('sorteddictCE_space','w'), fieldnames= ['A', 'B', 'C'], delimiter="\t")

for row in reader:
    print row['A']
    writer.writerow({'A':row['A'],'B':row['B'],'C':row['C']})
