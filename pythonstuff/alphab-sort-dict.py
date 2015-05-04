#!/usr/bin/env python
#-*- coding:utf-8 -*
import os,sys,csv
csv.field_size_limit(sys.maxsize)

a = 'hello'
print a
 
reader = csv.DictReader(open('dict','r'), fieldnames = ['A', 'B'], delimiter="\t", quoting=csv.QUOTE_NONE)
reader = sorted(reader, key=lambda some: some['A']);
writer = csv.DictWriter(open('sorteddict','w'), fieldnames= ['A', 'B'], delimiter="\t")

for row in reader:
    print row['A']
    writer.writerow({'A':row['A'],'B':row['B']})