#import glob, os, fileinput


#for filename in glob.glob('*.txt'):

#path = '/Users/Chris/work/sparx-marvel/sparx/apps/admin/templates'

#for filename in glob.glob(os.path.join(path, '*.ejs')):
#    print filename

# We only need to import this module
import os.path, re
 
# The top argument for walk. The
# Python27/Lib/site-packages folder in my case
 
#topdir = '/Users/Chris/work/sparx-marvel/sparx/apps/admin/templates'
topdir = '/Users/Chris/work/20150925ejsalwork/tmptmp' 
# The arg argument for walk, and subsequently ext for step
exten = '.ejs'
 
def step(ext, dirname, names):
    ext = ext.lower()
 
    for name in names:
        if name.lower().endswith(ext):
            fullfilename = (os.path.join(dirname, name))
            print fullfilename
            f = open (fullfilename, 'r+')

            #print 
            content = f.read()
            m = re.findall('\>(.*?)\<', content)
            print m
            m = re.sub('\>(.*?)\<'
                #, '><@- AL( \1 ) @><' 
                , lambda m: ''.join('><@-AL("%s")@><' % s if n % 2 == 0 
                         else s for n, s in enumerate(m.groups()))
                , content)
            #m = re. findall('(<@-AL\(")\b?("\)@>)', m)
            #m = re. findall('(<@-AL\(")\b?("\)@>)', m)
            #print m
            #m = re.sub('(<@-AL\(")\b?("\)@>)'
            #    , ' '
            #    , m)
            f.write('')
            f.write(m)
            #print m



 
# Start the walk
os.path.walk(topdir, step, exten)
