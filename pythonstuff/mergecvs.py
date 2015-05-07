#!/usr/bin/env python
#-*- coding:utf-8 -*
import os,sys,csv
csv.field_size_limit(sys.maxsize)

a = 'hello'
print a
 
with open('20150506', 'rb') as readf:
    reader = csv.DictReader(readf, fieldnames = ['A', 'B'], delimiter="\t", quoting=csv.QUOTE_NONE)
    writer = csv.DictWriter(open("dict",'a'), fieldnames=reader.fieldnames, delimiter="\t")

    for row in reader:
        print row['A']
    #if (row['B'] != ""):
        writer.writerow({'A':row['A'],'B':row['B']})
    #else:
    #    pass