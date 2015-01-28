# Sakshyam Dahal
# Bioinformatics Algorithm
# Date: Jan 28


# Read the data into a dataframe called data
data <- read.table("output.txt", header = T)

# Print histogram for chromosome
chromosome = as.numeric(data$Chromosome)

hist(chromosome, xlab="chromosome")