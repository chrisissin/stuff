#!/usr/bin/env python
#-*- coding:utf-8 -*
import os,sys,csv
csv.field_size_limit(sys.maxsize)

a = 'hello'
print a
 
contents=os.listdir('/home/chrisho/dev/gossip-queryvssat/out1/pagerank_data_10/') #contents of the current directory
files =[]
for i in contents:
    if os.path.isfile('/home/chrisho/dev/gossip-queryvssat/out1/pagerank_data_10/'+i) == True :
        files.append('/home/chrisho/dev/gossip-queryvssat/out1/pagerank_data_10/'+i)
#printing contents
for j in files:
    #choice = raw_input("Dou you want to print file  %s (y/n): "%j)
    #if choice == 'y':
        print "**************************"
        print "Printing Files %s" %j
        print "**************************"
        
        with open(j) as samplefile:
            reader = csv.DictReader(samplefile, fieldnames = ['A', 'B'], delimiter="\t", quoting=csv.QUOTE_NONE)
            writer = csv.DictWriter(open('dict','a'), fieldnames=reader.fieldnames, delimiter="\t")
            #writer.writerows(reader)
            for row in reader:
            #   print row['A']
                if (row['B'] != ""):
                    #row['B'] = '0'
                    writer.writerow({'A':row['A'],'B':row['B']})
    #else:
    #    pass