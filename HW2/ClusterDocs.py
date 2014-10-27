#!/usr/bin/env python
# Created by Joe Ellis 

# Libraries
import os, sys

# Scientific Libraries
from sklearn import cluster
from nltk.stem import WordNetLemmatizer
from nltk.tokenize.punkt import PunktWordTokenizer
from bs4 import BeautifulSoup

# Let's create a cluster of documents
class DocumentClusterer():
	""" Class that completes documents clustering"""

	def __init__(self, input_doc_dir):
		self.input_dir = input_doc_dir
		pass

	def create_dictionary():
		""" This function creates the dictionary"""

		files = os.listdir(self.input_doc_dir)
		for f in files:
			with open(f, "r") as g:
				f.writ

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
	prepare_NYTimes_dir("../Yahoo_Datasets/wiki_short.xml", "../Yahoo_Datasets/wiki_arts")



