#!/usr/bin/env python
# Created by Joe Ellis 

# Libraries
import os, sys
import pickle

# Scientific Libraries
import numpy as np
from sklearn import cluster
from sklearn.decomposition import TruncatedSVD
from sklearn.feature_extraction.text import TfidfVectorizer
from nltk.stem import WordNetLemmatizer
from nltk.tokenize.punkt import PunktWordTokenizer
from nltk.corpus import stopwords
from bs4 import BeautifulSoup
import scipy.sparse as sparse

# Let's create a cluster of documents
class DocumentClusterer():
    """ Class that completes documents clustering"""

    def __init__(self, input_doc_dir=False, save_file=False, files_list=False):
        
        # This 
        self.input_dir = input_doc_dir
        
        # This is the file for saving the files
        self.save_file = save_file
        self.files_list = files_list

        pass

    def create_dictionary(self):
        """ This function creates the dictionary"""

        if self.input_dir is not False:
            assert("No Input dir")
        if self.save_file is not False:
            assert("No Save File")
        if self.files_list is not False:
            assert("No files list file")

        wnl = WordNetLemmatizer()
        pt = PunktWordTokenizer()
        files = os.listdir(self.input_dir)
        token_dicts = {}
        article_files = []
        for f in files:
            with open(os.path.join(self.input_dir,f), "r") as g:
               
                #tokens = pt.tokenize(g.read())

                #stemmed = [wnl.lemmatize(token) for token in tokens]
                #stemmed_nostop = [w for w in stemmed if w not in stopwords.words('english')]

                # Now let's hold all of the data in lists
                token_dicts[f] = g.read()
                #article_files.append(f)

        # Now let's initialize the tfIDF vectorizer
        print "Starting Vectorizer"
        tfidf = TfidfVectorizer(max_features=1000)
        matrix_val = tfidf.fit_transform(token_dicts.values())
        print type(matrix_val)

        mat_file = self.save_file + ".npy"
        self.matrix_val = matrix_val

        # Now write out this object
        pickle.dump(tfidf, open(self.save_file, "w"))

        # File lists
        with open(self.files_list, "w") as f:
            f.write("\n".join(token_dicts.keys()))
        self.file_names = token_dicts.keys()

        pass

    def perform_clustering(self, cluster_dir):
        """ This does the actual clustering"""

        # Do dimensionality reduction

        arr = self.matrix_val.todense()
        #svd = TruncatedSVD(n_components=1000)
        #svd.fit(arr)
        #arr = svd.transform(arr)

        clusterer = cluster.KMeans(n_clusters=20, verbose=1, n_init=1)
        cluster_centers = clusterer.fit_predict(arr)
        print cluster_centers
        # Now we have to try and save the cluster files

        if not os.path.exists(cluster_dir):
            os.mkdir(cluster_dir)

        for i in range(0,20):
            output_file = os.path.join(cluster_dir, "cluster_" + str(i) + ".txt")
            with open(output_file, "w") as f:

                for z,val in enumerate(cluster_centers):
                    if val == i:
                        f.write(self.file_names[z] + "\n")


        pass



# END CLASS
def prepare_NYTimes_dir(input_file, output_dir):
    """ This funciton prepares the NY Times directory"""
    
    if not os.path.exists(output_dir):
        os.mkdir(output_dir)

    soup = BeautifulSoup(open(input_file, "r").read())
    pages = soup.find_all("page")
    for page in pages:
        title_sec = page.find("title")
        t_string = title_sec.string.replace("/", "").replace(" ", "_")
        text_s = page.find("text").string

        # Now output the file
        output_file = os.path.join(output_dir, t_string + ".txt")
        with open(output_file, "w") as f:
            f.write(text_s.encode("ascii", "ignore"))
    pass

if __name__ == "__main__":
    """ output the new directories"""
    #prepare_NYTimes_dir("../Yahoo_Datasets/wiki_short.xml", "../Yahoo_Datasets/wiki_arts")
    dc = DocumentClusterer("../Yahoo_Datasets/wiki_arts", "../Yahoo_Datasets/tf_idf.pickle", "../Yahoo_Datasets/article_names.txt")
    dc.create_dictionary()
    dc.perform_clustering("../Yahoo_Datasets/cluster_dir")


